package com.example.schedule.repository;

import com.example.schedule.dto.AuthorRequestDto;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Author;
import com.example.schedule.exception.AuthorNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateAuthorRepository implements AuthorRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateAuthorRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public AuthorResponseDto createAuthor(Author author) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("author").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", author.getName());
        parameters.put("email", author.getEmail());
        parameters.put("createdAt", author.getCreatedAt());
        parameters.put("modifiedAt", author.getModifiedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new AuthorResponseDto(
                key.longValue(),
                author.getName(),
                author.getEmail(),
                author.getCreatedAt(),
                author.getModifiedAt()
        );

    }

    @Override
    public List<AuthorResponseDto> getAuthors() {
        String sql = "select * from author ORDER BY modifiedAt DESC";
        return jdbcTemplate.query(sql, authorRowMapper());
    }

    @Override
    public Author getAuthorByIdOrElseThrow(Long id) {
        String sql = "select * from author where id = ?";
        List<Author> result = jdbcTemplate.query(sql, authorRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new AuthorNotFoundException(id));
    }

    @Override
    public int updateAuthor(Author author) {
        String sql = "update author set name = ?, email = ?, modifiedAt = ? where id = ?";
        return jdbcTemplate.update(
                sql,
                author.getName(),
                author.getEmail(),
                author.getModifiedAt(),
                author.getId());
    }

    @Override
    public int deleteAuthor(Long id) {
        String sql = "delete from author where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean existsById(Long id) {
        String sql = "select count(*) from author where id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count != null && count > 0;
    }

    private RowMapper<AuthorResponseDto> authorRowMapper() {
        return new RowMapper<AuthorResponseDto>() {
            @Override
            public AuthorResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new AuthorResponseDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("modifiedAt").toLocalDateTime()
                );
            }
        };
    }

    private RowMapper<Author> authorRowMapperV2() {
        return new RowMapper<Author>() {
            @Override
            public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Author(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("modifiedAt").toLocalDateTime()
                );
            }
        };
    }
}

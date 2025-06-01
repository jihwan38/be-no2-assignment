package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleAndAuthorResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.exception.ScheduleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcTemplateScheduleRepository implements ScheduleRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateScheduleRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public ScheduleAndAuthorResponseDto createSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("schedule")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("authorId", schedule.getAuthorId());
        parameters.put("password", schedule.getPassword());
        parameters.put("createdAt", schedule.getCreatedAt());
        parameters.put("modifiedAt", schedule.getModifiedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        // 생성된 스케줄 ID로 다시 조회 (작성자 이름 포함된 DTO로)
        return getScheduleAndAuthorById(key.longValue());
    }



    @Override
    public List<ScheduleAndAuthorResponseDto> getAllSchedules() {
        String sql = """
        SELECT s.id, s.todo, s.authorId, a.name AS authorName,
               s.createdAt, s.modifiedAt
        FROM schedule s
        JOIN author a ON s.authorId = a.id
        ORDER BY s.modifiedAt DESC
    """;
        return jdbcTemplate.query(sql, scheduleWithAuthorRowMapper());
    }


    @Override
    public List<ScheduleAndAuthorResponseDto> getSchedulesByAuthor(Long authorId) {
        String sql = """
        SELECT s.id, s.todo, s.authorId, a.name AS authorName,
               s.createdAt, s.modifiedAt
        FROM schedule s
        JOIN author a ON s.authorId = a.id
        WHERE s.authorId = ?
        ORDER BY s.modifiedAt DESC
    """;

        return jdbcTemplate.query(sql, scheduleWithAuthorRowMapper(), authorId);
    }



    @Override
    public List<ScheduleAndAuthorResponseDto> getSchedulesByModifiedAt(String modifiedAt) {
        String sql = """
        SELECT s.id, s.todo, s.authorId, a.name AS authorName,
               s.createdAt, s.modifiedAt
        FROM schedule s
        JOIN author a ON s.authorId = a.id
        WHERE DATE(s.modifiedAt) = ?
        ORDER BY s.modifiedAt DESC
    """;
        return jdbcTemplate.query(sql, scheduleWithAuthorRowMapper(), modifiedAt);
    }


    @Override
    public List<ScheduleAndAuthorResponseDto> getSchedulesByAuthorAndModifiedAt(Long authorId, String modifiedAt) {
        String sql = """
        SELECT s.id, s.todo, s.authorId, a.name AS authorName,
               s.createdAt, s.modifiedAt
        FROM schedule s
        JOIN author a ON s.authorId = a.id
        WHERE s.authorId = ? AND DATE(s.modifiedAt) = ?
        ORDER BY s.modifiedAt DESC
    """;
        return jdbcTemplate.query(sql, scheduleWithAuthorRowMapper(), authorId, modifiedAt);
    }

    @Override
    public ScheduleAndAuthorResponseDto getScheduleAndAuthorById(Long id) {
        String sql = """
        SELECT s.id, s.todo, s.authorId, a.name AS authorName,
               s.createdAt, s.modifiedAt
        FROM schedule s
        JOIN author a ON s.authorId = a.id
        WHERE s.id = ?
    """;

        return jdbcTemplate.query(sql, scheduleWithAuthorRowMapper(), id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new ScheduleNotFoundException(id));
    }



    @Override
    public Schedule getScheduleByIdOrElseThrow(Long id) {
        String sql = "select * from schedule where id = ?";
        List<Schedule> result = jdbcTemplate.query(sql, scheduleRowMapperV2(), id);
        return result.stream().findAny().orElseThrow(() -> new ScheduleNotFoundException(id));
    }

    @Override
    public int updateSchedule(Schedule schedule) {
        String sql = "update schedule set todo = ?, authorId = ?, modifiedAt = ? where id = ?";
        return jdbcTemplate.update(sql, schedule.getTodo(), schedule.getAuthorId(), schedule.getModifiedAt(), schedule.getId());

    }

    @Override
    public int deleteSchedule(Long id) {
        String sql = "delete from schedule where id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public List<ScheduleAndAuthorResponseDto> getSchedulesAndAuthorByPage(int page, int size) {
        String sql = """
        SELECT s.id, s.todo, s.authorId, a.name AS authorName,
               s.createdAt, s.modifiedAt
        FROM schedule s
        JOIN author a ON s.authorId = a.id
        ORDER BY s.modifiedAt DESC
        LIMIT ? OFFSET ?
    """;
        int offset = page * size;
        return jdbcTemplate.query(sql, scheduleWithAuthorRowMapper(), size, offset);
    }



    private RowMapper<Schedule> scheduleRowMapperV2(){
        return new RowMapper<Schedule>() {
            @Override
            public Schedule mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Schedule(
                        rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getLong("authorId"),
                        rs.getString("password"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("modifiedAt").toLocalDateTime()
                );
            }
        };
    }



    private RowMapper<ScheduleAndAuthorResponseDto> scheduleWithAuthorRowMapper() {
        return (rs, rowNum) -> new ScheduleAndAuthorResponseDto(
                rs.getLong("id"),
                rs.getString("todo"),
                rs.getLong("authorId"),
                rs.getString("authorName"),
                rs.getTimestamp("createdAt").toLocalDateTime(),
                rs.getTimestamp("modifiedAt").toLocalDateTime()
        );
    }




}

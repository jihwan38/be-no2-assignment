package com.example.schedule.repository;

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
    public ScheduleResponseDto createSchedule(Schedule schedule) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("schedule").usingGeneratedKeyColumns("id");


        Map<String, Object> parameters = new HashMap<>();
        parameters.put("todo", schedule.getTodo());
        parameters.put("authorId", schedule.getAuthorId());
        parameters.put("password", schedule.getPassword());
        parameters.put("createdAt", schedule.getCreatedAt());
        parameters.put("modifiedAt", schedule.getModifiedAt());

        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));

        return new ScheduleResponseDto(
                key.longValue(),
                schedule.getTodo(),
                schedule.getAuthorId(),
                schedule.getCreatedAt(),
                schedule.getModifiedAt()
        );

    }


    @Override
    public List<ScheduleResponseDto> getAllSchedules() {

        return jdbcTemplate.query("select * from schedule ORDER BY modifiedAt DESC", scheduleRowMapper());
    }

    @Override
    public List<ScheduleResponseDto> getSchedulesByAuthor(Long authorId) {
        String sql = "select * from schedule where authorId = ? ORDER BY modifiedAt DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper(), authorId);
    }


    @Override
    public List<ScheduleResponseDto> getSchedulesByModifiedAt(String modifiedAt) {
        String sql = "select * from schedule where DATE(modifiedAt) = ? ORDER BY modifiedAt DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper(), modifiedAt);
    }


    @Override
    public List<ScheduleResponseDto> getSchedulesByAuthorAndModifiedAt(Long authorId, String modifiedAt) {
        String sql = "select * from schedule where authorId = ? and DATE(modifiedAt) = ? ORDER BY modifiedAt DESC";
        return jdbcTemplate.query(sql, scheduleRowMapper(), authorId, modifiedAt);
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


    private RowMapper<ScheduleResponseDto> scheduleRowMapper(){
        return new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new ScheduleResponseDto(
                        rs.getLong("id"),
                        rs.getString("todo"),
                        rs.getLong("authorId"),
                        rs.getTimestamp("createdAt").toLocalDateTime(),
                        rs.getTimestamp("modifiedAt").toLocalDateTime()
                );
            }
        };
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

    private RowMapper<Schedule> scheduleRowMapperV3(){
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


}

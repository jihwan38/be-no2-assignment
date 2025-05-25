package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleResponseDto createSchedule(Schedule schedule);
    List<ScheduleResponseDto> getAllSchedules();
    List<ScheduleResponseDto> getSchedulesByAuthor(String author);
    List<ScheduleResponseDto> getSchedulesByModifiedAt(String ModifiedAt);
    List<ScheduleResponseDto> getSchedulesByAuthorAndModifiedAt(String author, String modifiedAt);
    Schedule getScheduleByIdOrElseThrow(Long id);
    int updateSchedule(Schedule schedule);
    int deleteSchedule(Long id);
}

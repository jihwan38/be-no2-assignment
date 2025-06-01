package com.example.schedule.repository;

import com.example.schedule.dto.ScheduleAndAuthorResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;

import java.util.List;

public interface ScheduleRepository {
    ScheduleAndAuthorResponseDto createSchedule(Schedule schedule);
    List<ScheduleAndAuthorResponseDto> getAllSchedules();
    List<ScheduleAndAuthorResponseDto> getSchedulesByAuthor(Long authorId);
    List<ScheduleAndAuthorResponseDto> getSchedulesByModifiedAt(String ModifiedAt);
    List<ScheduleAndAuthorResponseDto> getSchedulesByAuthorAndModifiedAt(Long authorId, String modifiedAt);
    ScheduleAndAuthorResponseDto getScheduleAndAuthorById(Long id);
    Schedule getScheduleByIdOrElseThrow(Long id);
    int updateSchedule(Schedule schedule);
    int deleteSchedule(Long id);
    List<ScheduleAndAuthorResponseDto> getSchedulesAndAuthorByPage(int page, int size);
}

package com.example.schedule.service;

import com.example.schedule.dto.*;

import java.util.List;

public interface ScheduleService {
    ScheduleAndAuthorResponseDto createSchedule(ScheduleCreateRequestDto scheduleRequestDto);
    List<ScheduleAndAuthorResponseDto> getSchedules(Long authorId, String modifiedAt);
    ScheduleAndAuthorResponseDto getScheduleById(Long id);
    ScheduleAndAuthorResponseDto updateSchedule(Long id, ScheduleUpdateRequestDto scheduleRequestDto);
    void deleteSchedule(Long id, ScheduleDeleteRequestDto scheduleRequestDto);
    List<ScheduleAndAuthorResponseDto> getSchedulesByPage(int page, int size);
}

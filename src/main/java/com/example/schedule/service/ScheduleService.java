package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;

import java.util.List;

public interface ScheduleService {
    ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto);
    List<ScheduleResponseDto> getSchedules(Long authorId, String modifiedAt);
    ScheduleResponseDto getScheduleById(Long id);
    ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto);
    void deleteSchedule(Long id, ScheduleRequestDto scheduleRequestDto);
}

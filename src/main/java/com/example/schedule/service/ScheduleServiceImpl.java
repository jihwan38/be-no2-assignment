package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = new Schedule(
                scheduleRequestDto.getTodo(),
                scheduleRequestDto.getAuthor(),
                scheduleRequestDto.getPassword()
        );

        return scheduleRepository.createSchedule(schedule);


    }



    @Override
    public List<ScheduleResponseDto> getSchedules(String author, String modifiedAt) {
        if(author != null && modifiedAt != null) {
            return scheduleRepository.getSchedulesByAuthorAndModifiedAt(author, modifiedAt);
        }else if(author != null){
            return scheduleRepository.getSchedulesByAuthor(author);
        }else if(modifiedAt != null){
            return scheduleRepository.getSchedulesByModifiedAt(modifiedAt);
        }else{
            return scheduleRepository.getAllSchedules();
        }
    }

    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }
}

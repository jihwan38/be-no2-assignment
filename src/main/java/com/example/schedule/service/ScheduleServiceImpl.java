package com.example.schedule.service;

import com.example.schedule.dto.ScheduleRequestDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.ScheduleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    //Lv1 일정 생성
    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(
                scheduleRequestDto.getTodo(),
                scheduleRequestDto.getAuthor(),
                scheduleRequestDto.getPassword(),
                now,
                now
        );
        return scheduleRepository.createSchedule(schedule);
    }

    //Lv1 전체 일정 조회
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

    //Lv1 선택 일정 조회
    @Override
    public ScheduleResponseDto getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);
        return new ScheduleResponseDto(schedule);
    }

    //Lv2 선택 일정 수정
    @Transactional
    @Override
    public ScheduleResponseDto updateSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);

        if(!schedule.getPassword().equals(scheduleRequestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }

        if(scheduleRequestDto.getAuthor() != null)schedule.setAuthor(scheduleRequestDto.getAuthor());
        if(scheduleRequestDto.getTodo() != null)schedule.setTodo(scheduleRequestDto.getTodo());
        schedule.setModifiedAt(LocalDateTime.now());

        int updatedRow = scheduleRepository.updateSchedule(schedule);

        if(updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }

        return new ScheduleResponseDto(schedule);

    }

    //Lv2 선택 일정 삭제
    @Override
    public void deleteSchedule(Long id, ScheduleRequestDto scheduleRequestDto) {
        Schedule schedule = scheduleRepository.getScheduleByIdOrElseThrow(id);
        if(!schedule.getPassword().equals(scheduleRequestDto.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Wrong password");
        }
        int deleteRow = scheduleRepository.deleteSchedule(id);
        if(deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found");
        }
    }


}

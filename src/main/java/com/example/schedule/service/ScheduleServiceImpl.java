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
    private final AuthorService authorService;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository, AuthorService authorService) {
        this.scheduleRepository = scheduleRepository;
        this.authorService = authorService;
    }

    //Lv1 일정 생성
    @Override
    public ScheduleResponseDto createSchedule(ScheduleRequestDto scheduleRequestDto) {
        //Lv3 작성자가 없으면 생성하지 못함
        boolean isExist = authorService.existsById(scheduleRequestDto.getAuthorId());
        if (!isExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }

        LocalDateTime now = LocalDateTime.now();
        Schedule schedule = new Schedule(
                scheduleRequestDto.getTodo(),
                scheduleRequestDto.getAuthorId(),
                scheduleRequestDto.getPassword(),
                now,
                now
        );
        return scheduleRepository.createSchedule(schedule);
    }

    //Lv1 전체 일정 조회
    @Override
    public List<ScheduleResponseDto> getSchedules(Long authorId, String modifiedAt) {
        if(authorId != null && modifiedAt != null) {
            return scheduleRepository.getSchedulesByAuthorAndModifiedAt(authorId, modifiedAt);
        }else if(authorId != null){
            return scheduleRepository.getSchedulesByAuthor(authorId);
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

        if(scheduleRequestDto.getAuthorId() != null)schedule.setAuthorId(scheduleRequestDto.getAuthorId());
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

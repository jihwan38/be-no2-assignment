package com.example.schedule.controller;


import com.example.schedule.dto.*;
import com.example.schedule.exception.InvalidPageRequestException;
import com.example.schedule.service.ScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {
    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }
    //Lv1 일정 생성
    @PostMapping
    public ResponseEntity<ScheduleAndAuthorResponseDto> createSchedule(@Valid @RequestBody ScheduleCreateRequestDto scheduleRequestDto) {
        return new ResponseEntity<>(scheduleService.createSchedule(scheduleRequestDto), HttpStatus.CREATED);
    }
    //Lv1 전체 일정 조회 & Lv3
    @GetMapping
    public ResponseEntity<List<ScheduleAndAuthorResponseDto>> getSchedules(
            @RequestParam(required = false) Long authorId,
            @RequestParam(required = false) String modifiedAt
    ){
        return new ResponseEntity<>(scheduleService.getSchedules(authorId, modifiedAt), HttpStatus.OK);
    }
    //Lv1 선택 일정 조회 & Lv3
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleAndAuthorResponseDto> getScheduleById(@PathVariable Long id){
        return new ResponseEntity<>(scheduleService.getScheduleById(id), HttpStatus.OK);
    }

    //Lv4 페이지네이션
    @GetMapping("/page")
    public ResponseEntity<List<ScheduleAndAuthorResponseDto>> getSchedulesWithPaging(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        if (page < 0 || size <= 0) {
            if (page < 0 || size <= 0) {
                throw new InvalidPageRequestException("페이지 번호는 0 이상, 크기는 1 이상이어야 합니다.");
            }

        }
        return new ResponseEntity<>(scheduleService.getSchedulesByPage(page, size), HttpStatus.OK);
    }
    //Lv2 선택 일정 수정 & Lv3
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleAndAuthorResponseDto> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleUpdateRequestDto scheduleRequestDto
    ){
        return new ResponseEntity<>(scheduleService.updateSchedule(id, scheduleRequestDto), HttpStatus.OK);
    }
    //Lv2 선택 일정 삭제 & Lv3
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleDeleteRequestDto scheduleRequestDto){
        scheduleService.deleteSchedule(id, scheduleRequestDto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

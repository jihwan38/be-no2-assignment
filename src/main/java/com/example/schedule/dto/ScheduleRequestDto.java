package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto { ;
    private String todo;
    private Long authorId;
    private String password;
}

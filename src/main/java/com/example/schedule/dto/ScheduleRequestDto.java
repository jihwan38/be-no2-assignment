package com.example.schedule.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ScheduleRequestDto { ;
    private String todo;
    private String author;
    private String password;
}

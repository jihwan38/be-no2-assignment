package com.example.schedule.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScheduleAndAuthorResponseDto {
    private Long id;
    private String todo;
    private Long authorId;
    private String authorName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}


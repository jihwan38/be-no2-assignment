package com.example.schedule.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Schedule {
    private Long id;

    @Setter
    private String todo;

    @Setter
    private Long authorId;

    private String password;
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime modifiedAt;

    public Schedule(String todo, Long authorId, String password, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.todo = todo;
        this.authorId = authorId;
        this.password = password;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Schedule(Long id, String todo, Long authorId, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.todo = todo;
        this.authorId = authorId;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}

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
    private String author;

    private String password;
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime modifiedAt;

    public Schedule(String todo, String author, String password, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.todo = todo;
        this.author = author;
        this.password = password;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Schedule(Long id, String todo, String author, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.todo = todo;
        this.author = author;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }
}

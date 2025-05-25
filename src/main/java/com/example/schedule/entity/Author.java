package com.example.schedule.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Author {
    private Long id;

    @Setter
    private String name;

    @Setter
    private String email;
    private LocalDateTime createdAt;

    @Setter
    private LocalDateTime modifiedAt;

    public Author(String name, String email, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.name = name;
        this.email = email;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

}

package com.example.schedule.repository;

import com.example.schedule.dto.AuthorRequestDto;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.entity.Author;

import java.util.List;

public interface AuthorRepository {
    AuthorResponseDto createAuthor(Author author);
    List<AuthorResponseDto> getAuthors();
    Author getAuthorByIdOrElseThrow(Long id);
    int updateAuthor(Author author);
    int deleteAuthor(Long id);
    boolean existsById(Long id);
}

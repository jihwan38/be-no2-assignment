package com.example.schedule.service;


import com.example.schedule.dto.AuthorRequestDto;
import com.example.schedule.dto.AuthorResponseDto;

import java.util.List;

public interface AuthorService {
    AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto);
    List<AuthorResponseDto> getAuthors();
    AuthorResponseDto getAuthorById(Long id);
    AuthorResponseDto updateAuthor(Long id, AuthorRequestDto authorRequestDto);
    void deleteAuthor(Long id);
    boolean existsById(Long id);
}

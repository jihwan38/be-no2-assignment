package com.example.schedule.service;

import com.example.schedule.dto.AuthorRequestDto;
import com.example.schedule.dto.AuthorResponseDto;
import com.example.schedule.dto.ScheduleResponseDto;
import com.example.schedule.entity.Author;
import com.example.schedule.entity.Schedule;
import com.example.schedule.repository.AuthorRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public AuthorResponseDto createAuthor(AuthorRequestDto authorRequestDto) {
        LocalDateTime now = LocalDateTime.now();

        Author author = new Author(
                authorRequestDto.getName(),
                authorRequestDto.getEmail(),
                now,
                now
        );
        return authorRepository.createAuthor(author);
    }

    @Override
    public List<AuthorResponseDto> getAuthors() {
        return authorRepository.getAuthors();
    }

    @Override
    public AuthorResponseDto getAuthorById(Long id) {
        Author author = authorRepository.getAuthorByIdOrElseThrow(id);
        return new AuthorResponseDto(author);
    }

    @Transactional
    @Override
    public AuthorResponseDto updateAuthor(Long id, AuthorRequestDto authorRequestDto) {
        Author author = authorRepository.getAuthorByIdOrElseThrow(id);

        if(authorRequestDto.getName() != null)author.setName(authorRequestDto.getName());
        if(authorRequestDto.getEmail() != null)author.setEmail(authorRequestDto.getEmail());

        author.setModifiedAt(LocalDateTime.now());

        int updatedRow = authorRepository.updateAuthor(author);

        if(updatedRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }

        return new AuthorResponseDto(author);
    }

    @Override
    public void deleteAuthor(Long id) {
        int deleteRow = authorRepository.deleteAuthor(id);
        if(deleteRow == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Author not found");
        }
    }

    @Override
    public boolean existsById(Long id) {
        return authorRepository.existsById(id);
    }
}

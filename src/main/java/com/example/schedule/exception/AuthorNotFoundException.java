package com.example.schedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(Long id) {

      super("작성자 id = " + id + "를 찾을 수 없습니다.");
    }
}

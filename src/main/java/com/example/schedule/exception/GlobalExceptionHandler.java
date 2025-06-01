package com.example.schedule.exception;


import com.example.schedule.exception.AuthorNotFoundException;
import com.example.schedule.exception.InvalidPasswordException;
import com.example.schedule.exception.ScheduleNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(AuthorNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleAuthorNotFound(AuthorNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }


    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleScheduleNotFound(ScheduleNotFoundException e) {
        return buildResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }


    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, Object>> handlePasswordMismatch(InvalidPasswordException e) {
        return buildResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" | "));
        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateKey(DuplicateKeyException e) {
        return buildResponse(HttpStatus.CONFLICT, "이미 등록된 이메일입니다.");
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleUnexpected(Exception e) {
        e.printStackTrace();
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 오류가 발생했습니다.");
    }

    @ExceptionHandler(InvalidPageRequestException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidPageRequest(InvalidPageRequestException e) {
        return buildResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }



    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return new ResponseEntity<>(body, status);
    }
}

package com.example.schedule.exception;

public class ScheduleNotFoundException extends RuntimeException {
    public ScheduleNotFoundException(Long id) {

      super("id = " + id + "번 일정을 찾을 수 없음");
    }
}

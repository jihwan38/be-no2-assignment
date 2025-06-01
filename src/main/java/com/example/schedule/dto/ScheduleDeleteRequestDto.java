package com.example.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ScheduleDeleteRequestDto {

    //Lv6 Validation
    @NotBlank(message = "비밀번호는 필수입력입니다.")
    private String password;
}

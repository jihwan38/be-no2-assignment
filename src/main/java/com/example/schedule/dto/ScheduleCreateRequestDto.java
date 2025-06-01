package com.example.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ScheduleCreateRequestDto {
    //Lv6 Validation
    @NotBlank(message = "할일은 필수입력입니다.")
    @Size(max = 200, message = "할일의 최대 입력은 200자까지입니다.")
    private String todo;

    @NotNull(message = "작성자 ID는 필수입니다.")
    private Long authorId;

    @NotBlank(message = "비밀번호는 필수입력입니다.")
    private String password;
}
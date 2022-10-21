package com.decagon.rewardyourteacherapi11bjavapodf2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExceptionResponse {
    private String message;
    private LocalDateTime timeStamp;
    private HttpStatus status;
}

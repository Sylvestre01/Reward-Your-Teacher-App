package com.decagon.rewardyourteacherapi11bjavapodf2.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTeacherProfileResponse {

    private String message;
    private LocalDateTime timeStamp;
}

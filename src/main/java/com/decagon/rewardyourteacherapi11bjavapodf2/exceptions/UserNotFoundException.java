package com.decagon.rewardyourteacherapi11bjavapodf2.exceptions;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException{
    private final  String message;

    public UserNotFoundException(String message) {
        this.message = message;
    }
}

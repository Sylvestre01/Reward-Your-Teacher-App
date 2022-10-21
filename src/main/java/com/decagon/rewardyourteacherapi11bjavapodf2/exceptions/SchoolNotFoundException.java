package com.decagon.rewardyourteacherapi11bjavapodf2.exceptions;

public class SchoolNotFoundException extends RuntimeException{
    private String message;

    public SchoolNotFoundException(String message) {
        this.message = message;
    }
}

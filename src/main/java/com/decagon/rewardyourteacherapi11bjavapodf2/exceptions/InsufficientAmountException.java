package com.decagon.rewardyourteacherapi11bjavapodf2.exceptions;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InsufficientAmountException extends RuntimeException{
    private final String message;

}

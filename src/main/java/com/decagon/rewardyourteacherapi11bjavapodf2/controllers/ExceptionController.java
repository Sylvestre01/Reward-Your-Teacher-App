package com.decagon.rewardyourteacherapi11bjavapodf2.controllers;

import com.decagon.rewardyourteacherapi11bjavapodf2.exceptions.*;
import com.decagon.rewardyourteacherapi11bjavapodf2.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> userNotFoundException(UserNotFoundException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ApiResponse<?>> userAlreadyExistException(UserAlreadyExistException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.CONFLICT), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> walletNotFoundException(WalletNotFoundException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SchoolNotFoundException.class)
    public ResponseEntity<ApiResponse<?>> schoolNotFoundException(SchoolNotFoundException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OAuth2AuthenticationException.class)
    public ResponseEntity<ApiResponse<?>> oauth2AuthenticationException(OAuth2AuthenticationException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.NOT_FOUND), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenRequestException.class)
    public ResponseEntity<ApiResponse<?>> invalidTokenRequestException(InvalidTokenRequestException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE), HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(InsufficientAmountException.class)
    public ResponseEntity<ApiResponse<?>> insufficientAmountException(InsufficientAmountException exception){
        return new ResponseEntity<>(new ApiResponse<>(exception.getMessage(), LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE), HttpStatus.NOT_ACCEPTABLE);
    }

}





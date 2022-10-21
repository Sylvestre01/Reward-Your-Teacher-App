package com.decagon.rewardyourteacherapi11bjavapodf2.exceptions;

public class WalletNotFoundException extends RuntimeException{

    private String message;

    public WalletNotFoundException(String message) {
        this.message = message;
    }
}

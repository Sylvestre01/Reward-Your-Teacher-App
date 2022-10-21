package com.decagon.rewardyourteacherapi11bjavapodf2.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@Getter
@Setter
@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class InvalidTokenRequestException extends RuntimeException {

	@Serial
    private static final long serialVersionUID = 1L;
	private final String tokenType;
    private final String token;
    private final String message;

    public InvalidTokenRequestException(String tokenType, String token, String message) {
        super(String.format("%s: [%s] token: [%s] ", message, tokenType, token));
        this.tokenType = tokenType;
        this.token = token;
        this.message = message;
    }
}

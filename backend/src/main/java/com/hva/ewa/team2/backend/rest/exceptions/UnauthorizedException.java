package com.hva.ewa.team2.backend.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        this("You are not permitted to perform this action.");
    }

    public UnauthorizedException(String message) {
        super(message);
    }

}

package com.example.financeData.exceptions;

public class UnauthorizedException extends RuntimeException{

    public UnauthorizedException(String message){
        super(message);
    }
}

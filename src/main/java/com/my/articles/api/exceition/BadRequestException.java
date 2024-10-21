package com.my.articles.api.exceition;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
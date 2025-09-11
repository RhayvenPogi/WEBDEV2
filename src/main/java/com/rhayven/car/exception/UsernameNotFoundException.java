package com.rhayven.car.exception;

public class UsernameNotFoundException extends RuntimeException{
    UsernameNotFoundException(String message){
        super(message);
    }
}
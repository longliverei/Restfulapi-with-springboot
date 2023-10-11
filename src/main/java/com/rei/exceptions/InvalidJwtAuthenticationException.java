package com.rei.exceptions;

public class InvalidJwtAuthenticationException extends RuntimeException {

    public InvalidJwtAuthenticationException(String ex) { super(ex); }

}
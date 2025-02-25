package com.example.planboardapi.exception.custom;

public class PasswordMismatchException extends RuntimeException {

    public PasswordMismatchException() {
        super("Password and confirmation password do not match");
    }
}

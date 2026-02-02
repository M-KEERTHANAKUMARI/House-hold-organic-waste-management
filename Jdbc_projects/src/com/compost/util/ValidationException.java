package com.compost.util;

public class ValidationException extends Exception {

    public ValidationException(String message) {
        super(message);   // ⭐ THIS IS THE FIX
    }

    @Override
    public String toString() {
        return getMessage();
    }
}

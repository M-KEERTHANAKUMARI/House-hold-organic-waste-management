package com.compost.util;

public class PendingAmountExistsException extends Exception {
	private String message;

    public PendingAmountExistsException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return message;
    }
}

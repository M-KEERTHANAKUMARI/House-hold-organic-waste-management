package com.compost.util;

public class CollectionWeightExceededException extends Exception {
	private String message;

    public CollectionWeightExceededException(String message) {
       super(message);
    }

    @Override
    public String toString() {
        return message;
    }
}


package com.example.demo.exceptions;

import com.mongodb.MongoException;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String errorMessage) {
        super(errorMessage);
    }
}

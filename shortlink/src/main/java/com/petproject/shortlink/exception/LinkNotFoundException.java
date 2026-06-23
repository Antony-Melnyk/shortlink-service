package com.petproject.shortlink.exception;

public class LinkNotFoundException extends RuntimeException {

    public LinkNotFoundException(String shortCode) {
        super("Link not found with shortCode: " + shortCode);
    }
}
// MusicCategoryNotFoundException.java - Exception class
package com.asgteach.springbootrest.service;

public class MusicCategoryNotFoundException extends RuntimeException {
    public MusicCategoryNotFoundException(String exception) {
        super(exception);
    }   
}

package com.mynewuber.project.uber.UberApp.exceptions;

public class ResourceNotFoundException extends RuntimeException{

	 public ResourceNotFoundException() {
	    }

	    public ResourceNotFoundException(String message) {
	        super(message);
	    }
}

package com.example.demo;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;


@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ExceptionFromURL extends RuntimeException {
	



	public ExceptionFromURL(String message) {
		super(message); 
    }

}

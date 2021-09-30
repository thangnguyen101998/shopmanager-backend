package com.fa.training.shopmanager.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
	
public static final String ERROR_CODE = "User.Notfound" ;
	
	public UserNotFoundException(String message) {
		super(message);
	}

}

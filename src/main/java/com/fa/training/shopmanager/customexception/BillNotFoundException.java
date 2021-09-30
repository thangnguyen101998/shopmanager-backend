package com.fa.training.shopmanager.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class BillNotFoundException extends RuntimeException {
	
	public static final String ERROR_CODE="Bill.NotFound";
	
	public BillNotFoundException(String message) {
		super(message);
	}

}

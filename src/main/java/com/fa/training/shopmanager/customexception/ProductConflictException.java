package com.fa.training.shopmanager.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductConflictException extends RuntimeException{

	public static final String ERROR_CODE = "Product.Conflict";
	
	public ProductConflictException(String message) {
		super(message);
	}
}

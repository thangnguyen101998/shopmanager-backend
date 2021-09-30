package com.fa.training.shopmanager.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductNotFoundException extends RuntimeException {

	public static final String ERROR_CODE = "Product.NotFound";
	
	public ProductNotFoundException(String message) {
		super(message);
	}
}

package com.fa.training.shopmanager.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ProductCategoryNotFoundException extends RuntimeException{

	public static final String ERROR_CODE = "ProductCategory.NotFound";
	
	public ProductCategoryNotFoundException(String message) {
		super(message);
	}
}

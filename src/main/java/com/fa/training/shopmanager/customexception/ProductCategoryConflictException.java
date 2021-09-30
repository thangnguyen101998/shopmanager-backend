package com.fa.training.shopmanager.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProductCategoryConflictException extends RuntimeException {
	
	public static final String  ERROR_CODE = "ProductCategory.Conflict";
	
	public ProductCategoryConflictException(String message) {
		super(message);
	}

}

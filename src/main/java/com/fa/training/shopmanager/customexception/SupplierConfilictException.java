package com.fa.training.shopmanager.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class SupplierConfilictException extends RuntimeException {
	
	public static final String ERROR_CODE = "Supplier.Conflict";
	
	public SupplierConfilictException(String message) {
		super(message);
	}

}

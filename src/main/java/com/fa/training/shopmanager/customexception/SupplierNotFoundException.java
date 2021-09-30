package com.fa.training.shopmanager.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SupplierNotFoundException extends RuntimeException {
	
	public static final String ERROR_CODE = "Supplier.Notfound" ;
	
	public SupplierNotFoundException(String message) {
		super(message);
	}

}

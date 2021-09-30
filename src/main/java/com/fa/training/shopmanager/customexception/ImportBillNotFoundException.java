package com.fa.training.shopmanager.customexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ImportBillNotFoundException extends RuntimeException{
	
	public static final String ERROR_CODE = "ImportBill.NotFound";
	
	public ImportBillNotFoundException(String message) {
		super(message);
	}

}

package com.fa.training.shopmanager.apicontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.fa.training.shopmanager.customexception.ExceptionResponseDTO;
import com.fa.training.shopmanager.customexception.ImportBillNotFoundException;
import com.fa.training.shopmanager.customexception.ProductCategoryConflictException;
import com.fa.training.shopmanager.customexception.ProductCategoryNotFoundException;
import com.fa.training.shopmanager.customexception.ProductConflictException;
import com.fa.training.shopmanager.customexception.ProductNotFoundException;
import com.fa.training.shopmanager.customexception.SupplierConfilictException;
import com.fa.training.shopmanager.customexception.SupplierNotFoundException;
import com.fa.training.shopmanager.customexception.UserNotFoundException;

@ControllerAdvice
@RestController
public class ResponseEntityExceptionController {
	
	@ExceptionHandler(value=ProductCategoryConflictException.class)
	public ResponseEntity<?> handelProductCategoryConfilicException(ProductCategoryConflictException ex){
		ExceptionResponseDTO response = new ExceptionResponseDTO(ex.getMessage(), ProductCategoryConflictException.ERROR_CODE);
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value=ProductCategoryNotFoundException.class)
	public ResponseEntity<?> handelProductCategoryNotFoundException(ProductNotFoundException ex){
		ExceptionResponseDTO response = new ExceptionResponseDTO(ex.getMessage(), ProductNotFoundException.ERROR_CODE);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value=SupplierConfilictException.class)
	public ResponseEntity<?> handelSupplierNameConfilicException(SupplierConfilictException ex){
		ExceptionResponseDTO response = new ExceptionResponseDTO(ex.getMessage(), SupplierConfilictException.ERROR_CODE);
		return new ResponseEntity<>(response, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value=SupplierNotFoundException.class)
	public ResponseEntity<?> handelSupplierNotFoundException(SupplierNotFoundException ex){
		ExceptionResponseDTO response = new ExceptionResponseDTO(ex.getMessage(), SupplierNotFoundException.ERROR_CODE);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value=UserNotFoundException.class)
	public ResponseEntity<?> handelUserNotFoundException(UserNotFoundException ex) {
		ExceptionResponseDTO response = new ExceptionResponseDTO(ex.getMessage(), UserNotFoundException.ERROR_CODE);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value=ProductConflictException.class)
	public ResponseEntity<?> handelProductConflictException(ProductConflictException ex) {
		ExceptionResponseDTO response = new ExceptionResponseDTO(ex.getMessage(), ProductConflictException.ERROR_CODE);
		return new ResponseEntity<>(response,HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(value = ProductNotFoundException.class)
	public ResponseEntity<?> handelProductNotFoundException(ProductNotFoundException ex){
		ExceptionResponseDTO response = new ExceptionResponseDTO(ex.getMessage(), ProductNotFoundException.ERROR_CODE);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(value = ImportBillNotFoundException.class)
	public ResponseEntity<?> handelImportBillNotFoundException(ImportBillNotFoundException ex){
		ExceptionResponseDTO response = new ExceptionResponseDTO(ex.getMessage(), ImportBillNotFoundException.ERROR_CODE);
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}

}

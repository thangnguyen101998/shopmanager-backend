package com.fa.training.shopmanager.dto;

import java.util.Date;

public interface CustomResultImportBill {
	
	Long getId();
	
	String getName();
	
	Integer getQuantity();
	
	Double getTotal();
	
	Double getPrice();
	
	Date getCreatedDate();
	
	String getSupplierName();

}

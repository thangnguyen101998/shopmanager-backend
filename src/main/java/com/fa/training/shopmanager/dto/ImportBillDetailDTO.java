package com.fa.training.shopmanager.dto;

import lombok.Data;

@Data
public class ImportBillDetailDTO {
	
	private Long id;
	
	private ProductDTO product;
	
	private double price;
	
	private int quantity;
	
	private Long importBillId;

}

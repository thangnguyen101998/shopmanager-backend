package com.fa.training.shopmanager.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class ImportBillDTO {
	
	private Long id;
	
	private String supplierId;

	private String userId;
	
	private Date createdDate;
	
	private List<ImportBillDetailDTO> importBillDetails = new ArrayList<>();
	
}

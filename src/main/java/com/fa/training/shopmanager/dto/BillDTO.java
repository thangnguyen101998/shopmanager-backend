package com.fa.training.shopmanager.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class BillDTO {
	
	private Long id;

	private String userId;
	
	private Date createdDate;
	
	private List<BillDetailDTO> billDetails = new ArrayList<>();
}

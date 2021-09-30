package com.fa.training.shopmanager.service;

import java.util.List;

import com.fa.training.shopmanager.model.BillDetails;

public interface BillDetailService {
	
	BillDetails saveBillDetails(BillDetails billDetails);
	
	List<BillDetails> findAllByBillDetailsByBillId(Long id);
}

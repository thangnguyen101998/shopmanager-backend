package com.fa.training.shopmanager.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fa.training.shopmanager.dto.BillDTO;
import com.fa.training.shopmanager.model.Bill;

public interface BillService {
	
	Bill saveBill(BillDTO billDTO);
	
	Page<Bill> findByDeletedIsFalseOrderByIdDesc(Pageable pageable);
	
	Bill findById(Long id);
	
	void deleteBillById(Long id);

}

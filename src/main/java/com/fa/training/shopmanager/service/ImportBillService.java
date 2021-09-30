package com.fa.training.shopmanager.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fa.training.shopmanager.dto.CustomResultImportBill;
import com.fa.training.shopmanager.model.ImportBill;

public interface ImportBillService {
	
	ImportBill saveImportBill(ImportBill importBill);
	
	Page<ImportBill> findAllByDeleteIsFalseOrderByIdDesc(Pageable pageable);
	
	Page<ImportBill> findByUserFullName(String fullName, Pageable pageable);
	
	Optional<ImportBill> findByIdAndDeletedIsFalse(Long id);
	
	void updateImportBill(ImportBill importBill);
	
	Page<CustomResultImportBill> findQuantityProduct(Pageable pageable);
	
	CustomResultImportBill countQuantityAndTotalImportBill();
	
	void deleteImportBill(Long id);
	
	Page<CustomResultImportBill> findImportBillDetailByProductId(Long id, Pageable pageable);

}

package com.fa.training.shopmanager.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.training.shopmanager.dto.CustomResultImportBill;
import com.fa.training.shopmanager.model.ImportBill;
import com.fa.training.shopmanager.repository.ImportBillRepository;
import com.fa.training.shopmanager.service.ImportBillService;

@Service
@Transactional
public class ImportBillServiceImpl implements ImportBillService {
	
	@Autowired
	private ImportBillRepository importBillRepository;

	@Override
	public ImportBill saveImportBill(ImportBill importBill) {
		return importBillRepository.save(importBill);
	}

	@Override
	public Page<ImportBill> findAllByDeleteIsFalseOrderByIdDesc(Pageable pageable) {
		return importBillRepository.findAllByDeletedIsFalseOrderByIdDesc(pageable);
	}

	@Override
	public Optional<ImportBill> findByIdAndDeletedIsFalse(Long id) {
		return importBillRepository.findByIdAndDeletedIsFalse(id);
	}

	@Override
	public Page<ImportBill> findByUserFullName(String fullName, Pageable pageable) {
		return importBillRepository.findByUserFullNameContainingIgnoreCaseAndDeletedIsFalseOrderByUserFullNameDesc(fullName, pageable);
	}

	@Override
	public void updateImportBill(ImportBill importBill) {
		importBillRepository.updateImportBill(importBill.getSupplierId(), importBill.getUserId(), 
				importBill.getTotal(), importBill.getCreateDate(), importBill.getId());
	}

	@Override
	public Page<CustomResultImportBill> findQuantityProduct(Pageable pageable) {
		return importBillRepository.findQuantityProduct(pageable);
	}

	@Override
	public CustomResultImportBill countQuantityAndTotalImportBill() {
		return importBillRepository.countQuantityAndTotalImportBill();
	}

	@Override
	public void deleteImportBill(Long id) {
		importBillRepository.deleteImportBill(id);
	}

	@Override
	public Page<CustomResultImportBill> findImportBillDetailByProductId(Long id, Pageable pageable) {
		return importBillRepository.findImportBillDetailByProductId(id, pageable);
	}

}

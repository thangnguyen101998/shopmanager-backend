package com.fa.training.shopmanager.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.training.shopmanager.model.ImportBillDetails;
import com.fa.training.shopmanager.repository.ImportBillDetailRepository;
import com.fa.training.shopmanager.service.ImportBillDetailService;

@Service
@Transactional
public class ImportBillDetailServiceImpl implements ImportBillDetailService {
	
	@Autowired
	private ImportBillDetailRepository importBillDetailRepository;

	@Override
	public ImportBillDetails saveImportBillDetails(ImportBillDetails importBillDetails) {
		return importBillDetailRepository.save(importBillDetails);
	}

	@Override
	public List<ImportBillDetails> findAllByImportBillId(Long id) {
		return importBillDetailRepository.findAllByImportBillIdOrderByIdDesc(id);
	}

	@Override
	public void updateImportBillDetail(ImportBillDetails importBillDetails) {
		importBillDetailRepository.updateBillDetail(importBillDetails.getPrice(), importBillDetails.getQuantity(), importBillDetails.getId());
	}

	@Override
	public void deleteImportBillDetail(Long id) {
		importBillDetailRepository.deleteImportBillDetail(id);
	}

	@Override
	public Optional<ImportBillDetails> findImportBillDetailsById(Long id) {
		return importBillDetailRepository.findById(id);
	}

}

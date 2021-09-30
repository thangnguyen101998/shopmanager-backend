package com.fa.training.shopmanager.service;

import java.util.List;
import java.util.Optional;

import com.fa.training.shopmanager.model.ImportBillDetails;

public interface ImportBillDetailService {
	
	ImportBillDetails saveImportBillDetails(ImportBillDetails importBillDetails);
	
	List<ImportBillDetails> findAllByImportBillId(Long id);

	void updateImportBillDetail(ImportBillDetails importBillDetails);
	
	void deleteImportBillDetail(Long id);
	
	Optional<ImportBillDetails> findImportBillDetailsById(Long id);
}

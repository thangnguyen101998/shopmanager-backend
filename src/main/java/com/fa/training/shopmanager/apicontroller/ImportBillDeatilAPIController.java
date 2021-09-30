package com.fa.training.shopmanager.apicontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fa.training.shopmanager.model.ImportBillDetails;
import com.fa.training.shopmanager.service.ImportBillDetailService;

@RestController
@RequestMapping("/api/import-bill-detail")
public class ImportBillDeatilAPIController {
	
	@Autowired
	private ImportBillDetailService importBillDetailService;

	@GetMapping("/by-import-bill-id/{id}")
	public ResponseEntity<?> findAllByImportBillId(@PathVariable("id")Long id){
		List<ImportBillDetails> importBillDetails = importBillDetailService.findAllByImportBillId(id);
		return new ResponseEntity<>(importBillDetails,HttpStatus.OK);
	}
}

package com.fa.training.shopmanager.apicontroller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fa.training.shopmanager.model.BillDetails;
import com.fa.training.shopmanager.service.BillDetailService;

@RestController
@RequestMapping("/api/bill-detail")
public class BillDetailAPIController {
	
	@Autowired
	private BillDetailService billDetailService;
	
	@GetMapping("/list/{id}")
	public ResponseEntity<?> getAllByBillId(@PathVariable("id")Long id){
		List<BillDetails> billDetails = billDetailService.findAllByBillDetailsByBillId(id);
		return new ResponseEntity<>(billDetails,HttpStatus.OK);
	}

}

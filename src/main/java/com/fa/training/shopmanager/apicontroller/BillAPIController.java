package com.fa.training.shopmanager.apicontroller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fa.training.shopmanager.dto.BillDTO;
import com.fa.training.shopmanager.model.Bill;
import com.fa.training.shopmanager.service.BillService;
import com.fa.training.shopmanager.validator.ValidatorBill;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/bills")
public class BillAPIController {
	
	@Autowired
	private BillService billService;
	
	Map<String, String> errors;
	
	@GetMapping("/{currentpage}/{pagesize}")
	public ResponseEntity<?> findAllBill(@PathVariable("currentpage")int currentPage,
			@PathVariable("pagesize")int pageSize){
		Page<Bill> bills = billService.findByDeletedIsFalseOrderByIdDesc(PageRequest.of(currentPage,pageSize));
		return new ResponseEntity<>(bills, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id){
		Bill bill = billService.findById(id);
		return new ResponseEntity<>(bill, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> saveBill(@RequestParam("bill")String billString) throws JsonMappingException, JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		BillDTO billDTO = objectMapper.readValue(billString,BillDTO.class);
		errors = ValidatorBill.validatorBill(billDTO);
		if(errors.size() > 0) {
			return new ResponseEntity<>(errors, HttpStatus.NOT_ACCEPTABLE);
		}
		Bill bill = billService.saveBill(billDTO);
		return new ResponseEntity<>(bill,HttpStatus.OK);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteBill(@PathVariable("id")Long id) {
		billService.deleteBillById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}

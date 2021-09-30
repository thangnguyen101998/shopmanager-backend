package com.fa.training.shopmanager.apicontroller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fa.training.shopmanager.customexception.ImportBillNotFoundException;
import com.fa.training.shopmanager.dto.CustomResultImportBill;
import com.fa.training.shopmanager.dto.ImportBillDTO;
import com.fa.training.shopmanager.dto.ImportBillDetailDTO;
import com.fa.training.shopmanager.model.ImportBill;
import com.fa.training.shopmanager.model.ImportBillDetails;
import com.fa.training.shopmanager.model.Product;
import com.fa.training.shopmanager.service.ImportBillDetailService;
import com.fa.training.shopmanager.service.ImportBillService;
import com.fa.training.shopmanager.service.ProductService;
import com.fa.training.shopmanager.validator.ValidateImportBill;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/api/import-bill")
public class ImportBillAPIController {
	
	@Autowired
	private ImportBillService importBillService;
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private ImportBillDetailService importBillDetailService;
	
	Map<String, String> errors;
	
	@GetMapping("/{currentpage}/{pagesize}")
	public ResponseEntity<?> findAllImportBill(@PathVariable("currentpage")int currentPage, 
			@PathVariable("pagesize")int pageSize){
		Page<ImportBill> importBills = importBillService.findAllByDeleteIsFalseOrderByIdDesc(PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(importBills, HttpStatus.OK);
	}
	
	@GetMapping("/{fullname}/{currentpage}/{pagesize}")
	public ResponseEntity<?> findByUserFullName(@PathVariable("fullname")String fullName,
			@PathVariable("currentpage")int currentPage, @PathVariable("pagesize")int pageSize){
		Page<ImportBill> importBills = importBillService.findByUserFullName(fullName, PageRequest.of(currentPage,pageSize));
		return new ResponseEntity<>(importBills, HttpStatus.OK);
	}
	
	@GetMapping("/find-by-product-id/{id}/{currentpage}/{pagesize}")
	public ResponseEntity<?> findByProductId(@PathVariable("id")Long id,@PathVariable("currentpage")int currentPage, 
			@PathVariable("pagesize")int pageSize){
		Page<CustomResultImportBill> customResultImportBills = importBillService.findImportBillDetailByProductId(id, PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(customResultImportBills, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addImportBill(@RequestParam("import-bill") String importBillString) throws JsonMappingException, JsonProcessingException{
		
		ObjectMapper objectMapper = new ObjectMapper();
		ImportBillDTO importBillDTO = objectMapper.readValue(importBillString, ImportBillDTO.class);
		errors = ValidateImportBill.validatorProduct(importBillDTO);
		if(errors.size() > 0) {
			return new ResponseEntity<>(errors,HttpStatus.NOT_ACCEPTABLE);
		}
		ImportBill importBill = new ImportBill();
		importBill.setCreateDate(convertToLocalDate(importBillDTO.getCreatedDate()));
		importBill.setSupplierId(Long.parseLong(importBillDTO.getSupplierId()));
		importBill.setUserId(Long.parseLong(importBillDTO.getUserId()));
		double total = 0;
		for(ImportBillDetailDTO importBillDetailDTO : importBillDTO.getImportBillDetails()) {
			total += importBillDetailDTO.getQuantity() * importBillDetailDTO.getPrice();
		}
		importBill.setTotal(total);
		
		ImportBill newImportBill = importBillService.saveImportBill(importBill);
		
		for(ImportBillDetailDTO importBillDetailDTO : importBillDTO.getImportBillDetails()) {
			Optional<Product> poroduct = productService.findProductByNameAndDeletedIsFalse(importBillDetailDTO.getProduct().getName());
			int quantity = poroduct.get().getQuantity() + importBillDetailDTO.getQuantity();
			productService.updateQuantityProduct(quantity, poroduct.get().getId());
			
			ImportBillDetails importBillDetails = new ImportBillDetails();
			importBillDetails.setImportBillId(newImportBill.getId());
			importBillDetails.setQuantity(importBillDetailDTO.getQuantity());
			importBillDetails.setPrice(importBillDetailDTO.getPrice());
			importBillDetails.setProductId(poroduct.get().getId());
			
			importBillDetailService.saveImportBillDetails(importBillDetails);
		}
		return new ResponseEntity<>(HttpStatus.OK);
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long id){
		Optional<ImportBill> importBillOp = importBillService.findByIdAndDeletedIsFalse(id);
		if(importBillOp.isEmpty()) {
			throw new ImportBillNotFoundException("* phiếu nhập hàng không tồn tại");
		}
		return new ResponseEntity<>(importBillOp.get(),HttpStatus.OK);
	}
	
	@PatchMapping("/edit")
	public ResponseEntity<?> editImportBill(@RequestParam("import-bill")String importBillJson) throws JsonMappingException, JsonProcessingException{
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		ImportBillDTO importBillDTO = objectMapper.readValue(importBillJson, ImportBillDTO.class);
		
		ImportBill importBill = new ImportBill();
		importBill.setCreateDate(convertToLocalDate(importBillDTO.getCreatedDate()));
		importBill.setSupplierId(Long.parseLong(importBillDTO.getSupplierId()));
		importBill.setUserId(Long.parseLong(importBillDTO.getUserId()));
		importBill.setId(importBillDTO.getId());
		double total = 0;
		for(ImportBillDetailDTO importBillDetailDTO : importBillDTO.getImportBillDetails()) {
			total += importBillDetailDTO.getQuantity() * importBillDetailDTO.getPrice();
		}
		importBill.setTotal(total);
		
		importBillService.updateImportBill(importBill);
		
		for(ImportBillDetailDTO importBillDetailDTO : importBillDTO.getImportBillDetails()) {
			Optional<ImportBillDetails> importBillDetailOp = importBillDetailService.findImportBillDetailsById(importBillDetailDTO.getId());
			Optional<Product> poroduct = productService.findProductByNameAndDeletedIsFalse(importBillDetailDTO.getProduct().getName());
			if(importBillDetailDTO.getQuantity() != importBillDetailOp.get().getQuantity()) {
				int quantity = poroduct.get().getQuantity() + (importBillDetailOp.get().getQuantity() - importBillDetailDTO.getQuantity());
				productService.updateQuantityProduct(quantity, poroduct.get().getId());
			}
			
			ImportBillDetails importBillDetails = new ImportBillDetails();
			importBillDetails.setId(importBillDetailDTO.getId());
			importBillDetails.setQuantity(importBillDetailDTO.getQuantity());
			importBillDetails.setPrice(importBillDetailDTO.getPrice());
			
			importBillDetailService.updateImportBillDetail(importBillDetails);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@GetMapping("/find-quantity-and-total/{currentpage}/{pagesize}")
	public ResponseEntity<?> findQuantityAndTotalProduct(@PathVariable("currentpage")int currentPage, 
			@PathVariable("pagesize")int pageSize){
		Page<CustomResultImportBill> customs = importBillService.findQuantityProduct(PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(customs,HttpStatus.OK);
	}
	
	@GetMapping("/count-quantity-total")
	public ResponseEntity<?> countQuantityAndTotalImportBill(){
		CustomResultImportBill customResultImportBill = importBillService.countQuantityAndTotalImportBill();
		return new ResponseEntity<>(customResultImportBill,HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteImportBill(@PathVariable("id") Long id){
		importBillService.deleteImportBill(id);
		importBillDetailService.deleteImportBillDetail(id);
		List<ImportBillDetails> importBillDetails = importBillDetailService.findAllByImportBillId(id);
		for(ImportBillDetails importBillDetail : importBillDetails) {
			Product product = productService.findProductByIdAndDeletedIsFalse(importBillDetail.getProductId());
			int quantity = product.getQuantity() - importBillDetail.getQuantity();
			productService.updateQuantityProduct(quantity, product.getId());
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	public LocalDate convertToLocalDate(Date date) {
        return Instant.ofEpochMilli(date.getTime())
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
    }
}

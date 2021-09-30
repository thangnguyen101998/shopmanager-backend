package com.fa.training.shopmanager.apicontroller;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fa.training.shopmanager.model.Supplier;
import com.fa.training.shopmanager.service.SupplierService;
import com.fa.training.shopmanager.utils.AppUtils;

@RestController
@RequestMapping("/api/suppliers")
public class SupplierAPIController {
	
	@Autowired
	private SupplierService supplierService;
	
	@Autowired
	private AppUtils appUtils;
	
	Map<String, String> errors;
	
	@GetMapping("/{currentpage}/{pagesize}")
	public ResponseEntity<?> getAllSupplier(@PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage) {
		Page<Supplier> suppliers = supplierService.showAll(PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<Page<Supplier>>(suppliers, HttpStatus.OK);
	}
	
	@GetMapping("/{search}/{currentpage}/{pagesize}")
	public ResponseEntity<?> findByNameContaining(@PathVariable("search")String search, @PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage){
		Page<Supplier> suppliers = supplierService.findBySupplierNameContaining(search, PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(suppliers,HttpStatus.OK);
	}
	
	@GetMapping("/order-by-supplier-name-desc/{currentpage}/{pagesize}")
	public ResponseEntity<?> orderBySupplierNameDesc(@PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage){
		Page<Supplier> suppliers = supplierService.findAllByDeletedIsFalseOrderBySupplierNameDesc(PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(suppliers,HttpStatus.OK);
	}
	
	@GetMapping("/order-by-supplier-name-asc/{currentpage}/{pagesize}")
	public ResponseEntity<?> orderBySupplierNameAsc(@PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage){
		Page<Supplier> suppliers = supplierService.findAllByDeletedIsFalseOrderBySupplierNameAsc(PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(suppliers,HttpStatus.OK);
	}
	
	@GetMapping("/all-list")
	public ResponseEntity<?> getAllSuppliers(){
		List<Supplier> suppliers = supplierService.findAllByDeletedIsFalse();
		return new ResponseEntity<>(suppliers,HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> saveSupplier(@Valid @RequestBody Supplier supplier, BindingResult result){
		if(result.hasErrors()) {
			return  appUtils.mapErrorToResponse(result);
		}
		Supplier newSupplier = supplierService.saveSupplier(supplier);
		return new ResponseEntity<>( newSupplier, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getSupplierById(@PathVariable Long id){
		Supplier supplier = supplierService.findSupplierById(id);
		return new ResponseEntity<>(supplier, HttpStatus.OK);
	}

	@PatchMapping("/edit")
	public ResponseEntity<?> updateSupplier(@Valid @RequestBody Supplier supplier, BindingResult result){
		if(result.hasErrors()) {
			return  appUtils.mapErrorToResponse(result);
		}
		supplierService.updateSupplier(supplier);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteSupplier(@PathVariable Long id){
		supplierService.deleteSupplier(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

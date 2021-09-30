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

import com.fa.training.shopmanager.model.ProductCategory;
import com.fa.training.shopmanager.service.ProductCategoryService;
import com.fa.training.shopmanager.utils.AppUtils;

@RestController
@RequestMapping("/api/product-categories")
public class ProductCategoryAPIController {

	@Autowired
	private ProductCategoryService productCategoryService;
	
	@Autowired
	private AppUtils appUtils;
	
	Map<String, String> errors;
	
	@GetMapping("/{currentpage}/{pagesize}")
	public ResponseEntity<?> getAllProductCategory(@PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage) {
		Page<ProductCategory> productCategories = productCategoryService.showAll(PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<Page<ProductCategory>>(productCategories, HttpStatus.OK);
	}
	
	@GetMapping("/{search}/{currentpage}/{pagesize}")
	public ResponseEntity<?> findByNameContaining(@PathVariable("search")String search, @PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage){
		Page<ProductCategory> productCategories = productCategoryService.findByNameContaining(search, PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(productCategories,HttpStatus.OK);
	}
	
	@GetMapping("/order-by-name-desc/{currentpage}/{pagesize}")
	public ResponseEntity<?> orderByNameDesc(@PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage){
		Page<ProductCategory> productCategories = productCategoryService.findAllByDeletedIsFalseOrderByNameDesc(PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(productCategories,HttpStatus.OK);
	}
	
	@GetMapping("/order-by-name-asc/{currentpage}/{pagesize}")
	public ResponseEntity<?> orderByNameAsc(@PathVariable("pagesize") int pageSize,
			@PathVariable("currentpage") int currentPage){
		Page<ProductCategory> productCategories = productCategoryService.findAllByDeletedIsFalseOrderByNameAsc(PageRequest.of(currentPage, pageSize));
		return new ResponseEntity<>(productCategories,HttpStatus.OK);
	}
	
	@GetMapping("/all-list")
	public ResponseEntity<?> findAllByStatusIsFalse(){
		List<ProductCategory> productCategories = productCategoryService.findAllByDeleteIsFalse();
		return new ResponseEntity<>(productCategories, HttpStatus.OK);
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> addProducCategory(@Valid @RequestBody ProductCategory productCategory,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return appUtils.mapErrorToResponse(bindingResult);
		}
		ProductCategory newProductCategory = productCategoryService.saveProductCategory(productCategory);
		return new ResponseEntity<ProductCategory>(newProductCategory, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findProductById(@PathVariable("id")Long id){
		ProductCategory productCategory = productCategoryService.findProductCategoryById(id);
		return new ResponseEntity<>(productCategory,HttpStatus.OK);
	}
	
	@PatchMapping("/edit")
	public ResponseEntity<?> updateProductCategory(@Valid @RequestBody ProductCategory productCategory, BindingResult result){
		if (result.hasErrors()) {
			return appUtils.mapErrorToResponse(result);
		}
		productCategoryService.updateProductCategory(productCategory);
		return new ResponseEntity<ProductCategory>(HttpStatus.OK);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletedProductCategory(@PathVariable Long id) {
		productCategoryService.deleteProductCategory(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
}

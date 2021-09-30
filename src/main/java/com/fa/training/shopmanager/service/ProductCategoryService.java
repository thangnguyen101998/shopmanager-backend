package com.fa.training.shopmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fa.training.shopmanager.model.ProductCategory;

public interface ProductCategoryService {
	
	Page<ProductCategory> showAll(Pageable pageable);
	
	Page<ProductCategory> findByNameContaining(String name, Pageable pageable);
	
	Page<ProductCategory> findAllByDeletedIsFalseOrderByNameDesc(Pageable pageable);
	
	Page<ProductCategory> findAllByDeletedIsFalseOrderByNameAsc(Pageable pageable);
	
	List<ProductCategory> findAllByDeleteIsFalse();
	
	ProductCategory saveProductCategory(ProductCategory productCategory);
	
	ProductCategory findProductCategoryById(Long id);
	
	void deleteProductCategory(Long id);
	
	Optional<ProductCategory> checkNameProductCategory( String name);
	
	void updateProductCategory(ProductCategory productCategory);
}

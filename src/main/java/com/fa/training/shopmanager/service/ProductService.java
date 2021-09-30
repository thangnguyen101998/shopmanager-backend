package com.fa.training.shopmanager.service;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.fa.training.shopmanager.model.Product;

public interface ProductService {
	
	Page<Product> findAllByDeletedIsFalse(Pageable pageable);
	
	Page<Product> findByNameContaining(String name,Pageable pageable);
	
	Page<Product> findAllByDeletedIsFalseOrderByNameDesc(Pageable pageable);
	
	Page<Product> findAllByDeletedIsFalseOrderByNameAsc(Pageable pageable);
	
	Page<Product> findAllByProductCateogryId(Long productCategoryId, Pageable pageable);
	
	List<Product> findAllProduct();
	
	Product saveProduct( Product product,HttpServletRequest request, MultipartFile image);
	
	Product findProductByIdAndDeletedIsFalse(Long id);
	
	Optional<Product> findProductByNameAndDeletedIsFalse(String name);
	
	void updateProduct(Product product,HttpServletRequest request, MultipartFile image);
	
	void deleteProduct(Long productId);
	
	void updateQuantityProduct(int quantity, Long productId);
	
	void deleteProducts(List<Long> ids);

}

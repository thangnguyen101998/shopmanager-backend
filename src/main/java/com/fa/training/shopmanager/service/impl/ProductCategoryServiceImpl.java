package com.fa.training.shopmanager.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.training.shopmanager.customexception.ProductCategoryConflictException;
import com.fa.training.shopmanager.customexception.ProductCategoryNotFoundException;
import com.fa.training.shopmanager.model.ProductCategory;
import com.fa.training.shopmanager.repository.ProductCategoryRepository;
import com.fa.training.shopmanager.service.ProductCategoryService;

@Service
@Transactional
public class ProductCategoryServiceImpl implements ProductCategoryService{
	
	@Autowired
	private ProductCategoryRepository productCategoryRepository;

	@Override
	public Page<ProductCategory> showAll(Pageable pageable) {
		return productCategoryRepository.findAllByDeletedIsFalseOrderByIdDesc(pageable);
	}

	@Override
	public List<ProductCategory> findAllByDeleteIsFalse() {
		return productCategoryRepository.findAllByDeletedIsFalse();
	}

	@Override
	public ProductCategory saveProductCategory(ProductCategory productCategory) {
		Optional<ProductCategory> productCategoryOp = checkNameProductCategory(productCategory.getName());
		if (!productCategoryOp.isEmpty()) {
			throw new ProductCategoryConflictException("* loại sản phẩm đã tồn tại");
		}
		productCategory.setCreatedDate(LocalDate.now());
		return productCategoryRepository.save(productCategory);
	}

	@Override
	public ProductCategory findProductCategoryById(Long id) {
		return productCategoryRepository.findByIdAndDeletedIsFalse(id)
				.orElseThrow(() -> new ProductCategoryNotFoundException("* không tìm thấy loại sản phẩm "));
	}

	@Override
	public void deleteProductCategory(Long id) {
		productCategoryRepository.deleteProducCategory(id);
	}

	@Override
	public Optional<ProductCategory> checkNameProductCategory(String name) {
		return productCategoryRepository.checkNameProductCategory(name);
	}

	@Override
	public Page<ProductCategory> findByNameContaining(String name, Pageable pageable) {
		return productCategoryRepository.findByNameContainingIgnoreCaseAndDeletedIsFalseOrderByNameDesc(name, pageable);
	}

	@Override
	public Page<ProductCategory> findAllByDeletedIsFalseOrderByNameDesc(Pageable pageable) {
		return productCategoryRepository.findAllByDeletedIsFalseOrderByNameDesc(pageable);
	}

	@Override
	public Page<ProductCategory> findAllByDeletedIsFalseOrderByNameAsc(Pageable pageable) {
		return productCategoryRepository.findAllByDeletedIsFalseOrderByNameAsc(pageable);
	}

	@Override
	public void updateProductCategory(ProductCategory productCategory) {
		Optional<ProductCategory> productCategoryOp = checkNameProductCategory(productCategory.getName());
		if (!productCategoryOp.isEmpty() && !productCategoryOp.get().getId().equals(productCategory.getId())) {
			throw new ProductCategoryConflictException("loại sản phẩm đã tồn tại");
		}
		productCategory.setModifiedDate(LocalDate.now());
		productCategoryRepository.updateProductCategory(productCategory.getName(), productCategory.getDescription(),
				productCategory.getModifiedDate(), productCategory.getId());
	}
	
	

}

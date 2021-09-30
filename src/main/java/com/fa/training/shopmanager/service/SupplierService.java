package com.fa.training.shopmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fa.training.shopmanager.model.Supplier;

public interface SupplierService {
	
	Page<Supplier> showAll(Pageable pageable);
	
	Page<Supplier> findBySupplierNameContaining(String name, Pageable pageable);
	
	Page<Supplier> findAllByDeletedIsFalseOrderBySupplierNameDesc(Pageable pageable);
	
	Page<Supplier> findAllByDeletedIsFalseOrderBySupplierNameAsc(Pageable pageable);
	
	List<Supplier> findAllByDeletedIsFalse();
	
	Supplier saveSupplier(Supplier supplier);
	
	Supplier findSupplierById(Long id);
	
	void deleteSupplier(Long id);
	
	Optional<Supplier> checkNameSupplier( String name);
	
	Optional<Supplier> checkEmailSupplier(String email);
	
	Optional<Supplier> checkPhoneNumberSupplier(String phoneNumber);
	
	void updateSupplier(Supplier supplier);

}

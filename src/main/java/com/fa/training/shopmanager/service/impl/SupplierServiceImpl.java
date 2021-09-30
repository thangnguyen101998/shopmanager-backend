package com.fa.training.shopmanager.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.training.shopmanager.customexception.SupplierConfilictException;
import com.fa.training.shopmanager.customexception.SupplierNotFoundException;
import com.fa.training.shopmanager.model.Supplier;
import com.fa.training.shopmanager.repository.SupplierReposiotry;
import com.fa.training.shopmanager.service.SupplierService;

@Service
@Transactional
public class SupplierServiceImpl implements SupplierService {
	
	@Autowired
	private SupplierReposiotry supplierReposiotry;

	@Override
	public Page<Supplier> showAll(Pageable pageable) {
		return supplierReposiotry.findAllSupplierByDeletedIsFalseOrderByIdDesc(pageable);
	}

	@Override
	public Page<Supplier> findBySupplierNameContaining(String name, Pageable pageable) {
		return supplierReposiotry.findBySupplierNameContainingIgnoreCaseAndDeletedIsFalseOrderBySupplierNameDesc(name, pageable);
	}

	@Override
	public Page<Supplier> findAllByDeletedIsFalseOrderBySupplierNameDesc(Pageable pageable) {
		return supplierReposiotry.findAllByDeletedIsFalseOrderBySupplierNameDesc(pageable);
	}

	@Override
	public Page<Supplier> findAllByDeletedIsFalseOrderBySupplierNameAsc(Pageable pageable) {
		return supplierReposiotry.findAllByDeletedIsFalseOrderBySupplierNameAsc(pageable);
	}

	@Override
	public List<Supplier> findAllByDeletedIsFalse() {
		return supplierReposiotry.findAllByDeletedIsFalseOrderByIdAsc();
	}

	@Override
	public Supplier saveSupplier(Supplier supplier) {
		Optional<Supplier> supplierNameOp = checkNameSupplier(supplier.getSupplierName());
		Optional<Supplier> supplierEmailOp = checkEmailSupplier(supplier.getEmail());
		Optional<Supplier> supplierPhoneOp = checkPhoneNumberSupplier(supplier.getPhoneNumber());
		if(!supplierNameOp.isEmpty()) {
			throw new SupplierConfilictException("* tên nhà cung cấp đã tồn tại");
		}
		if(!supplierEmailOp.isEmpty()) {
			throw new SupplierConfilictException("* email nhà cung cấp đã tồn tại");
		}
		if(!supplierPhoneOp.isEmpty()) {
			throw new SupplierConfilictException("* số điện thoại của nhà cung cấp đã tồn tại");
		}
		supplier.setCreatedDate(LocalDate.now());
		return supplierReposiotry.save(supplier);
	}

	@Override
	public Supplier findSupplierById(Long id) {
		return supplierReposiotry.findByIdAndDeletedIsFalse(id).orElseThrow(() -> new SupplierNotFoundException("* nhà cung cấp không tồn tại"));
	}

	@Override
	public void deleteSupplier(Long id) {
		supplierReposiotry.deleteSupplier(id);
	}

	@Override
	public Optional<Supplier> checkNameSupplier(String name) {
		return supplierReposiotry.findSupplierBySupplierNameAndDeletedIsFalse(name);
	}

	@Override
	public Optional<Supplier> checkEmailSupplier(String email) {
		return supplierReposiotry.findSupplierByEmailAndDeletedIsFalse(email);
	}

	@Override
	public Optional<Supplier> checkPhoneNumberSupplier(String phoneNumber) {
		return supplierReposiotry.findSupplierByPhoneNumberAndDeletedIsFalse(phoneNumber);
	}

	@Override
	public void updateSupplier(Supplier supplier) {
		Optional<Supplier> supplierNameOp =  checkNameSupplier(supplier.getSupplierName());
		Optional<Supplier> supplierEmailOp = checkEmailSupplier(supplier.getEmail());
		Optional<Supplier> supplierPhoneOp = checkPhoneNumberSupplier(supplier.getPhoneNumber());
		if(!supplierNameOp.isEmpty() && !supplierNameOp.get().getId().equals(supplier.getId())) {
			throw new SupplierConfilictException("* tên nhà cung cấp đã tồn tại");
		}
		if(!supplierEmailOp.isEmpty() && !supplierEmailOp.get().getId().equals(supplier.getId())) {
			
			throw new SupplierConfilictException("* email nhà cung cấp đã tồn tại");
		}
		if(!supplierPhoneOp.isEmpty() && !supplierPhoneOp.get().getId().equals(supplier.getId())) {
			throw new SupplierConfilictException("* số điện thoại của nhà cung cấp đã tồn tại");
		}
		supplier.setModifiedDate(LocalDate.now());
		supplierReposiotry.updateSupplier(supplier.getSupplierName(), supplier.getEmail(),
				supplier.getAddress(), supplier.getPhoneNumber(), supplier.getModifiedDate(),
				supplier.getId());
	}

}

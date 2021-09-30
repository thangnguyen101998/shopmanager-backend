package com.fa.training.shopmanager.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.training.shopmanager.model.Supplier;

public interface SupplierReposiotry extends JpaRepository<Supplier, Long>{
	
    Page<Supplier> findAllSupplierByDeletedIsFalseOrderByIdDesc(Pageable pageable);
    
    Page<Supplier> findBySupplierNameContainingIgnoreCaseAndDeletedIsFalseOrderBySupplierNameDesc(String name, Pageable pageable);
	
	Page<Supplier> findAllByDeletedIsFalseOrderBySupplierNameDesc(Pageable pageable);
	
	Page<Supplier> findAllByDeletedIsFalseOrderBySupplierNameAsc(Pageable pageable);
	
	List<Supplier> findAllByDeletedIsFalseOrderByIdAsc();
	
	Optional<Supplier> findSupplierBySupplierNameAndDeletedIsFalse(String supplierName);

	Optional<Supplier> findSupplierByEmailAndDeletedIsFalse(String email);

	Optional<Supplier> findSupplierByPhoneNumberAndDeletedIsFalse(String phoneNumber);

	Optional<Supplier> findByIdAndDeletedIsFalse(Long id);

	@Modifying
	@Query(value = "update supplier set supplier_name=:supplier_name, email=:email,address=:address,"
			+ "phone_number=:phone_number, modified_date=:modified_date where id =:id", nativeQuery = true)
	void updateSupplier(@Param("supplier_name") String supplierName, @Param("email") String email,
			@Param("address") String address, @Param("phone_number") String phoneNumber,
			@Param("modified_date") LocalDate modifiedDate, @Param("id") Long id);
	
	@Modifying
	@Query(value = "update supplier set deleted = 1 where id = :id",nativeQuery = true)
	void deleteSupplier(@Param("id") Long id);
}

package com.fa.training.shopmanager.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.training.shopmanager.model.Bill;

public interface BillRepository extends JpaRepository<Bill, Long>{

	Page<Bill> findByDeletedIsFalseOrderByIdDesc(Pageable pageable);
	
	Optional<Bill> findByIdAndDeletedIsFalse(Long id);
	
	@Modifying
	@Query(value = "update bill set deleted = 1 where id = :id", nativeQuery = true)
	void deleteBill(@Param("id") Long id);
}

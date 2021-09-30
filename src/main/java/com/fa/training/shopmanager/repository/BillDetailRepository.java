package com.fa.training.shopmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.training.shopmanager.model.BillDetails;

public interface BillDetailRepository extends JpaRepository<BillDetails, Long>{
	
	List<BillDetails> findAllByBillIdOrderByIdDesc(Long id);

	@Modifying
	@Query(value = "update bill_details set deleted = 1 where bill_id = :bill_id", nativeQuery = true)
	void deleteBillDetailByBillId(@Param("bill_id") Long id);
}

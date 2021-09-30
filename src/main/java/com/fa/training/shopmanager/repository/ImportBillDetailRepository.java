package com.fa.training.shopmanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.training.shopmanager.model.ImportBillDetails;

public interface ImportBillDetailRepository extends JpaRepository<ImportBillDetails, Long>{

	List<ImportBillDetails> findAllByImportBillIdOrderByIdDesc(Long importBillId);
	
	@Modifying
	@Query(value = "update import_bill_details set price=:price, quantity=:quantity where id=:id",nativeQuery = true)
	void updateBillDetail(@Param("price")double price, @Param("quantity")int quantity, @Param("id")Long id);
	
	@Modifying
	@Query(value = "update import_bill_details set deleted=1 where import_bill_id=:import_bill_id", nativeQuery = true)
	void deleteImportBillDetail(@Param("import_bill_id")Long id);
	
}

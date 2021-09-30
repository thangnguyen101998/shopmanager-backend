package com.fa.training.shopmanager.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fa.training.shopmanager.dto.CustomResultImportBill;
import com.fa.training.shopmanager.model.ImportBill;

public interface ImportBillRepository extends JpaRepository<ImportBill, Long>{
	
	Page<ImportBill> findAllByDeletedIsFalseOrderByIdDesc(Pageable pageable);
	
	Optional<ImportBill> findByIdAndDeletedIsFalse(Long id);
	
	Page<ImportBill> findByUserFullNameContainingIgnoreCaseAndDeletedIsFalseOrderByUserFullNameDesc(String fullName, Pageable pageable);
	
	@Modifying
	@Query(value = "update import_bill set deleted=1 where id=:id",nativeQuery = true)
	void deleteImportBill(@Param("id")Long id);
	
	@Modifying
	@Query(value="update import_bill set supplier_id=:supplier_id, user_id=:user_id,total=:total, "
			+ "create_date=:create_date where id=:id",nativeQuery = true)
	void updateImportBill(@Param("supplier_id")Long supplierId, @Param("user_id")Long userId, @Param("total")double total,
			@Param("create_date")LocalDate createDate, @Param("id")Long id);
	
	@Query(value ="with x(product_id,quantity,total) as( select product_id, sum(import_bill_details.quantity)as quantity, sum(import_bill_details.price * import_bill_details.quantity) as total\r\n"
			+ "		from import_bill_details\r\n"
			+ "		where import_bill_details.deleted = 0\r\n"
			+ "		group by import_bill_details.product_id)\r\n"
			+ "select product.id as id, name as name,x.quantity as quantity,x.total as total from x right join product on x.product_id = product.id group by product.name, product.id,x.quantity,x.total\r\n"
			+ "",
			 countQuery = "select count(distinct name) from import_bill_details right join product on import_bill_details.product_id = product.id", 
			nativeQuery = true)
	Page<CustomResultImportBill> findQuantityProduct(Pageable pageable);
	
	@Query(value = "select sum(import_bill_details.quantity)as quantity, sum(import_bill_details.price * import_bill_details.quantity) as total  \r\n"
			+ "from import_bill_details where import_bill_details.deleted=0",nativeQuery = true)
	CustomResultImportBill countQuantityAndTotalImportBill();
	
	@Query(value = "select product.name as name, import_bill_details.price as price, import_bill_details.quantity as quantity, import_bill.create_date as createdDate, supplier.supplier_name as supplierName\r\n"
			+ "from import_bill_details inner join product on import_bill_details.product_id = product.id \r\n"
			+ "	inner join import_bill on import_bill_details.import_bill_id = import_bill.id \r\n"
			+ "	inner join supplier on import_bill.supplier_id = supplier.id\r\n"
			+ "where product_id=:product_id and import_bill_details.deleted = 0",
			countQuery="select count(*) from product inner join import_bill_details on product.id = import_bill_details.product_id \r\n"
					+ "where import_bill_details.deleted = 0 and product_id=:product_id",
			nativeQuery = true)
	Page<CustomResultImportBill> findImportBillDetailByProductId(@Param("product_id") Long id, Pageable pageable);

}

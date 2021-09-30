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

import com.fa.training.shopmanager.model.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	
	Page<ProductCategory> findAllByDeletedIsFalseOrderByIdDesc(Pageable pageable);
	
	Page<ProductCategory> findByNameContainingIgnoreCaseAndDeletedIsFalseOrderByNameDesc(String name, Pageable pageable);
	
	Page<ProductCategory> findAllByDeletedIsFalseOrderByNameDesc(Pageable pageable);
	
	Page<ProductCategory> findAllByDeletedIsFalseOrderByNameAsc(Pageable pageable);
	
	List<ProductCategory> findAllByDeletedIsFalse();

	@Modifying
	@Query(value = "update product_category set deleted = 1 where id = :id",nativeQuery = true)
	void deleteProducCategory(@Param("id") Long id);
	
	@Query(value = "select * from product_category where deleted = 0 and name = :name",nativeQuery = true)
	Optional<ProductCategory> checkNameProductCategory(@Param("name") String name);
	
	Optional<ProductCategory> findByIdAndDeletedIsFalse(Long id);
	
	@Modifying
	@Query(value = "update product_category set name=:name, description=:description, modified_date=:modified_date where id =:id", nativeQuery = true)
	void updateProductCategory(@Param("name")String name, @Param("description") String description,@Param("modified_date")LocalDate modifiedDate, @Param("id") Long id);
}

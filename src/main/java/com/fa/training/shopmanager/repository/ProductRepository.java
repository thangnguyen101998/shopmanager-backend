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

import com.fa.training.shopmanager.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
	
	Page<Product> findAllByDeletedIsFalseOrderByIdDesc(Pageable pageable);
	
	Page<Product> findByNameContainingIgnoreCaseAndDeletedIsFalseOrderByNameDesc(String name, Pageable pageable);
	
	Page<Product> findAllByDeletedIsFalseOrderByNameDesc(Pageable pageable);
	
	Page<Product> findAllByDeletedIsFalseOrderByNameAsc(Pageable pageable);
	
	Page<Product> findAllByDeletedIsFalseAndProductCategoryIdOrderByIdDesc(Long id,Pageable pageable);
	
	List<Product> findAllByDeletedIsFalseOrderByIdDesc();
	
	Optional<Product> findProductByIdAndDeletedIsFalse(Long id);
	
	Optional<Product> findProductByNameAndDeletedIsFalse(String name);
	
	@Modifying
	@Query(value = "update product set deleted= 1 where id=:id",nativeQuery = true)
	void deleteProduct(@Param("id") Long id);
	
	@Modifying
	@Query(value = "update product set quantity=:quantity where id=:id", nativeQuery = true)
	void updateQuantityProduct(@Param("quantity") int quantity, @Param("id")Long id);
	
	@Modifying
	@Query(value = "update product set name=:name,price=:price, image_url=:image_url,description=:description,"
			+ " product_category_id=:product_category_id,modified_date=:modified_date where id=:id",nativeQuery = true)
	void updateProduct(@Param("name")String name,@Param("price")double price,
			@Param("image_url")String imageUrl,@Param("description")String description,
			@Param("product_category_id")Long productCategoryId,@Param("modified_date") LocalDate modifiedDate,
			@Param("id")Long id);
	
	

}

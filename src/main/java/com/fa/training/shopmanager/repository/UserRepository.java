package com.fa.training.shopmanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fa.training.shopmanager.model.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
	
	Page<Users> findAllByDeletedIsFalse(Pageable pageable);
	
	List<Users> findAllByDeletedIsFalseOrderByIdDesc();
	
	Optional<Users> findByEmailAndDeletedIsFalse(String email);
	
	

}

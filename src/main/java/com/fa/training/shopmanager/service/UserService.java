package com.fa.training.shopmanager.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.fa.training.shopmanager.model.Users;

public interface UserService {
	
	Page<Users> findAllByDeletedIsFalse(Pageable pageable);
	
	Page<Users> findByNameContaining(String name,Pageable pageable);
	
	Page<Users> findAllByDeletedIsFalseOrderByNameDesc(Pageable pageable);
	
	Page<Users> findAllByDeletedIsFalseOrderByNameAsc(Pageable pageable);
	
	List<Users> findAllByDeletedIsFalseOrderByIdAsc();
	
	Users saveUser( Users user);
	
	Optional<Users> findUserByIdAndDeletedIsFalse(Long id);
	
	Optional<Users> findUserByNameAndDeletedIsFalse(String name);
	
	Optional<Users> findUserByEmailAnDeletedIsFalse(String email);
	
	Optional<Users> findUserByPhoneNumberIsFalse(String phoneNumber);
	
	void updateUser(Users user);
	
	void deleteUser(Long userId);

}

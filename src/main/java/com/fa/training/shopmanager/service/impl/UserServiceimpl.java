package com.fa.training.shopmanager.service.impl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fa.training.shopmanager.model.Users;
import com.fa.training.shopmanager.repository.UserRepository;
import com.fa.training.shopmanager.service.UserService;

@Service
@Transactional
public class UserServiceimpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Page<Users> findAllByDeletedIsFalse(Pageable pageable) {
		return userRepository.findAllByDeletedIsFalse(pageable);
	}

	@Override
	public Page<Users> findByNameContaining(String name, Pageable pageable) {
		return null;
	}

	@Override
	public Page<Users> findAllByDeletedIsFalseOrderByNameDesc(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Users> findAllByDeletedIsFalseOrderByNameAsc(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Users saveUser(Users user) {
		return userRepository.save(user);
	}

	@Override
	public Optional<Users> findUserByIdAndDeletedIsFalse(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Users> findUserByNameAndDeletedIsFalse(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Users> findUserByEmailAnDeletedIsFalse(String email) {
		return userRepository.findByEmailAndDeletedIsFalse(email);
	}

	@Override
	public Optional<Users> findUserByPhoneNumberIsFalse(String phoneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateUser(Users user) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteUser(Long userId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Users> findAllByDeletedIsFalseOrderByIdAsc() {
		return userRepository.findAllByDeletedIsFalseOrderByIdDesc();
	}

}

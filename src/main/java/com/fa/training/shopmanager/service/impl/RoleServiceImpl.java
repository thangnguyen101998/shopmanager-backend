package com.fa.training.shopmanager.service.impl;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fa.training.shopmanager.model.Role;
import com.fa.training.shopmanager.repository.RoleRepostiory;
import com.fa.training.shopmanager.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepostiory roleRepostiory;

	@Override
	public Optional<Role> findByName(String name) {
		return roleRepostiory.findByName(name);
	}

}

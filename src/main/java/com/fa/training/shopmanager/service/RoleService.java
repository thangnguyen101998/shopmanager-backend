package com.fa.training.shopmanager.service;

import java.util.Optional;

import com.fa.training.shopmanager.model.Role;

public interface RoleService {

	Optional<Role> findByName(String name);
}

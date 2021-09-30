package com.fa.training.shopmanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fa.training.shopmanager.model.Role;

public interface RoleRepostiory extends JpaRepository<Role, Long>{
	
	Optional<Role> findByName(String name);

}

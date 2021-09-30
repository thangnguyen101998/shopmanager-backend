package com.fa.training.shopmanager.service.impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fa.training.shopmanager.model.Users;
import com.fa.training.shopmanager.service.UserService;

@Service
public class UserDetailServiceImpl implements UserDetailsService{
	
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Users> users = userService.findUserByEmailAnDeletedIsFalse(username);
		System.out.println(users.get().toString());
		if (users.get() == null) {
			throw new UsernameNotFoundException("user name not found");
		}
		Set<GrantedAuthority> authorities = new HashSet<>();
		String role = users.get().getRole().getName();
		authorities.add(new SimpleGrantedAuthority(role));
		return new User(users.get().getEmail(),users.get().getPassword(), authorities);
	}

}

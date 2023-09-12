package com.cosmo_bank.restapi.service.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cosmo_bank.restapi.entity.User;
import com.cosmo_bank.restapi.repository.UserRepository;
import com.cosmo_bank.restapi.service.UserService;

@Service(value = "userService")
public class UserServiceImpl implements UserService{
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), 
				user.getPassword(),
				getAuthorities(user));
	}

	private Set<SimpleGrantedAuthority> getAuthorities(User user) {
		
		return new HashSet<SimpleGrantedAuthority>(
				Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getUsertype())));
	}

	@Override
	public User addUser(User user) {
		return userRepository.save(user);
	}

}

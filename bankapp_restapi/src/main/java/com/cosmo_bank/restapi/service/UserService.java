package com.cosmo_bank.restapi.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.cosmo_bank.restapi.entity.User;

@Service(value = "userService")
public interface UserService extends UserDetailsService {
	
	public User addUser(User user);

}

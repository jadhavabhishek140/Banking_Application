package com.cosmo_bank.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cosmo_bank.restapi.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	public User findByUsername(String username);

}

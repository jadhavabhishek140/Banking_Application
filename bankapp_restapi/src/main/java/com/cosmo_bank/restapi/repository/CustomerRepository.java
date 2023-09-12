package com.cosmo_bank.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cosmo_bank.restapi.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	public Customer findByAccountAccountNumberAndPhoneNumber(String accountNumber, String phoneNumber);
	
}

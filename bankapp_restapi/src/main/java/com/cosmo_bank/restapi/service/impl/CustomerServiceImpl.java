package com.cosmo_bank.restapi.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmo_bank.restapi.dto.request.CustomerStatusRequest;
import com.cosmo_bank.restapi.entity.Customer;
import com.cosmo_bank.restapi.exception.CustomerNotFoundException;
import com.cosmo_bank.restapi.exception.UserAlreadyRegisteredException;
import com.cosmo_bank.restapi.repository.CustomerRepository;
import com.cosmo_bank.restapi.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	@Autowired
	CustomerRepository customerRepository;

	@Override
	public int isCustomerValid(CustomerStatusRequest custReq) throws CustomerNotFoundException, UserAlreadyRegisteredException {
		
		Customer customer = customerRepository.findByAccountAccountNumberAndPhoneNumber(custReq.getAccountNumber(), custReq.getPhoneNumber());
		
		if(customer == null) {
			throw new CustomerNotFoundException("Given details do not match with any existing customer.");
		}
		
		if(customer.getUser() != null) {
			throw new UserAlreadyRegisteredException("User is already registered for given customer.");
		}
		
		return customer.getId();
	}

	@Override
	public Customer getCustomer(int id) throws CustomerNotFoundException {
		
		Optional<Customer> optCust = customerRepository.findById(id);
		
		if(optCust.isEmpty()) {
			throw new CustomerNotFoundException("Customer for given customer Id does not Exist");
		}
		
		return optCust.get();
	}

	@Override
	public Customer addCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

}

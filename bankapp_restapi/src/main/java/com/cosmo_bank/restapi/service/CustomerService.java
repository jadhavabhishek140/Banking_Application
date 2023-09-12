package com.cosmo_bank.restapi.service;

import org.springframework.stereotype.Service;

import com.cosmo_bank.restapi.dto.request.CustomerStatusRequest;
import com.cosmo_bank.restapi.entity.Customer;
import com.cosmo_bank.restapi.exception.CustomerNotFoundException;
import com.cosmo_bank.restapi.exception.UserAlreadyRegisteredException;

@Service
public interface CustomerService {

	public int isCustomerValid(CustomerStatusRequest custReq) throws CustomerNotFoundException, UserAlreadyRegisteredException;
	
	public Customer getCustomer(int id) throws CustomerNotFoundException;
	
	public Customer addCustomer(Customer customer);

}

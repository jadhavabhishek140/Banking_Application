package com.cosmo_bank.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cosmo_bank.restapi.config.JwtUtil;
import com.cosmo_bank.restapi.dto.request.CustomerStatusRequest;
import com.cosmo_bank.restapi.dto.request.LoginRequest;
import com.cosmo_bank.restapi.dto.request.RegisterRequest;
import com.cosmo_bank.restapi.dto.response.CustomerStatusReponse;
import com.cosmo_bank.restapi.dto.response.LoginResponse;
import com.cosmo_bank.restapi.entity.Customer;
import com.cosmo_bank.restapi.entity.User;
import com.cosmo_bank.restapi.enums.Usertype;
import com.cosmo_bank.restapi.exception.CustomerNotFoundException;
import com.cosmo_bank.restapi.exception.UserAlreadyRegisteredException;
import com.cosmo_bank.restapi.service.CustomerService;
import com.cosmo_bank.restapi.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class UserController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtil jwtUtil;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CustomerService customerService;
	
	@PostMapping("/valid/cust")
	public ResponseEntity<Object> checkCustomerStatus(@RequestBody CustomerStatusRequest custReq) {
		
		try{
			int custId = customerService.isCustomerValid(custReq);
			
			CustomerStatusReponse csr = new CustomerStatusReponse(custId, "Customer Details Verified Successfully.");
			
			return new ResponseEntity<>(csr, HttpStatus.OK);
			
		} catch(CustomerNotFoundException e) {
			
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
			
		} catch (UserAlreadyRegisteredException e) {
			
			return new ResponseEntity<Object>(e.getMessage(), HttpStatus.CONFLICT);
			
		}
	}
	
	@PostMapping("/user/register")
	public ResponseEntity<Object> registerUser(@RequestBody RegisterRequest regReq) {
		
		if(!regReq.getPassword().equals(regReq.getConfirmPassword())) {
			return new ResponseEntity<>("Passwords do not match.", HttpStatus.BAD_REQUEST);
		}
		
		User user = new User(regReq.getUsername(), encoder.encode(regReq.getPassword()), Usertype.CUSTOMER);
		
		Customer customer;
		
		try {
			customer = customerService.getCustomer(regReq.getCustomerId());
		} catch (CustomerNotFoundException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
		if(customer.getUser() != null) {
			return new ResponseEntity<>("User is already registered for given Customer.", HttpStatus.BAD_REQUEST);
		}
		
		try {
			User savedUser = userService.addUser(user);
			
			customer.setUser(savedUser);
			
			customerService.addCustomer(customer);
			
			return new ResponseEntity<>("User Registration Successfull", HttpStatus.OK);
			
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}
	
	@PostMapping("/user/login")
	public ResponseEntity<Object> loginUser(@RequestBody LoginRequest loginReq){
		
		Authentication authentication;
		
		try {
			authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginReq.getUsername(), loginReq.getPassword()));
		} catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtUtil.generateToken(authentication);

		org.springframework.security.core.userdetails.User user = 
				(org.springframework.security.core.userdetails.User) authentication.getPrincipal();
		
		String role = user.getAuthorities().stream().map(n -> n.getAuthority().replace("ROLE_", "")).findAny().get();

		return new ResponseEntity<>(new LoginResponse(jwt, user.getUsername(), role), HttpStatus.OK);
		
	}

}

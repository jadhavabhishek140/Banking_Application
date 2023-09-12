package com.cosmo_bank.restapi.dto.request;

import lombok.Data;

@Data
public class RegisterRequest {
	
	private String username;
	
	private String password;
	
	private String confirm_password;
	
	private int customerId;

}

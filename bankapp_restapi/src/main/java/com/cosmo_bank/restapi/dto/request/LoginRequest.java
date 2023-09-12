package com.cosmo_bank.restapi.dto.request;

import lombok.Data;

@Data
public class LoginRequest {
	
	private String username;
	
	private String password;

}

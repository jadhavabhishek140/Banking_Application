package com.cosmo_bank.restapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponse {
	
	private String jwtToken;
	
	private String username;

	private String usertype;
}

package com.cosmo_bank.restapi.dto.request;

import lombok.Data;

@Data
public class CustomerStatusRequest {

	private String accountNumber;
	
	private String phoneNumber;
	
}

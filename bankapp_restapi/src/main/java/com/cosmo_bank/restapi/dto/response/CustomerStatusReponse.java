package com.cosmo_bank.restapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerStatusReponse {
	
	private int customerId;
	
	private String message;

}

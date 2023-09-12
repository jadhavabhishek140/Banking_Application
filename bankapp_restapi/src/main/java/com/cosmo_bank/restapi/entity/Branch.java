package com.cosmo_bank.restapi.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Branch {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String name;
	
	private String phone_no;
	
	private String email;
	
	private String IFSC_code;
	
	@OneToOne(optional=false, cascade = CascadeType.ALL)
	private Address address;

	public Branch(String name, String phone_no, String email, String iFSC_code, Address address) {
		super();
		this.name = name;
		this.phone_no = phone_no;
		this.email = email;
		IFSC_code = iFSC_code;
		this.address = address;
	}
}

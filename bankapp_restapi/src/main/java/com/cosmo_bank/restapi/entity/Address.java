package com.cosmo_bank.restapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@Entity
public class Address {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NonNull
	private String line1;
	
	private String line2;
	
	private String landmark;
	
	@NonNull
	private String city;
	
	@NonNull
	private String sub_district;
	
	@NonNull
	private String district;
	
	@NonNull
	private String state;
	
	@NonNull
	private String pincode;

	public Address(@NonNull String line1, String line2, String landmark, @NonNull String city,
			@NonNull String sub_district, @NonNull String district, @NonNull String state, @NonNull String pincode) {
		super();
		this.line1 = line1;
		this.line2 = line2;
		this.landmark = landmark;
		this.city = city;
		this.sub_district = sub_district;
		this.district = district;
		this.state = state;
		this.pincode = pincode;
	}
}

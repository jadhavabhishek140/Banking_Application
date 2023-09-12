package com.cosmo_bank.restapi.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "loginId")
	@JsonIgnore
	private User user;
	
	private String firstname;
	private String lastname;
	
	private LocalDate dateOfBirth;
	
	private String email;
	private String phoneNumber;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	private Address address;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "accountId")
	private Account account;

}

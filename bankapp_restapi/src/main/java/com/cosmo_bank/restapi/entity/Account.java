package com.cosmo_bank.restapi.entity;

import java.time.LocalDate;

import com.cosmo_bank.restapi.enums.AccountType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToOne(optional = true, cascade = CascadeType.ALL)
	@JoinColumn(name = "customer_id")
	private Customer customer;
	
	private String accountNumber;
	
	private AccountType accountType;
	
	private LocalDate openingDate;
	
	@ManyToOne(optional=true, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name = "branch_id")
	private Branch branch;

}

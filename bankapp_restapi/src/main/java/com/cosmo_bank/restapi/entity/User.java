package com.cosmo_bank.restapi.entity;

import com.cosmo_bank.restapi.enums.Usertype;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	private Usertype usertype;
	
	public User(String username, String password, Usertype usertype) {
		super();
		this.username = username;
		this.password = password;
		this.usertype = usertype;
	}
	
}

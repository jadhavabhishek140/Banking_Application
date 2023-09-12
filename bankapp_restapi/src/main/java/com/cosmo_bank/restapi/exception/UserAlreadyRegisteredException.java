package com.cosmo_bank.restapi.exception;

public class UserAlreadyRegisteredException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UserAlreadyRegisteredException(String msg) {
		super(msg);
	}

}

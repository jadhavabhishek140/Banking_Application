package com.cosmo_bank.restapi.service;

import org.springframework.stereotype.Service;

import com.cosmo_bank.restapi.entity.Branch;

@Service
public interface BranchService {
	
	public Branch getByNameAndIfscCode(String name, String IFSCcode);
}

package com.cosmo_bank.restapi.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cosmo_bank.restapi.entity.Branch;
import com.cosmo_bank.restapi.repository.BranchRepository;
import com.cosmo_bank.restapi.service.BranchService;

@Service
public class BranchServiceImpl implements BranchService {
	
	@Autowired
	BranchRepository branchRepository;

	@Override
	public Branch getByNameAndIfscCode(String name, String ifsc_code) {

		return branchRepository.findByNameAndIfscCode(name, ifsc_code);
	}

}

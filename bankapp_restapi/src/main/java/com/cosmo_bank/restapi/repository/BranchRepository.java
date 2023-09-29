package com.cosmo_bank.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cosmo_bank.restapi.entity.Branch;

public interface BranchRepository extends JpaRepository<Branch, Integer>{

	public Branch findByNameAndIfscCode(String name, String ifscCode);

}

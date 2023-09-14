package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.SmartBillingSystem.Entity.Branch;

public interface BrandRepo extends JpaRepository<Branch, Integer> {

}

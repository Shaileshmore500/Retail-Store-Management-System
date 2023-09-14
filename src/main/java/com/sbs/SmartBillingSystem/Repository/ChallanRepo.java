package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.SmartBillingSystem.Entity.Challan;

public interface ChallanRepo extends JpaRepository<Challan, Integer> {

}

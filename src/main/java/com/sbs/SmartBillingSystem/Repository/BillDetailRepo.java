package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.SmartBillingSystem.Entity.BillDetails;

public interface BillDetailRepo extends JpaRepository<BillDetails, Integer> {

}

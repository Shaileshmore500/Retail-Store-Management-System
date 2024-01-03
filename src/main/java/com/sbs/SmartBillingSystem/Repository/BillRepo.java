package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.SmartBillingSystem.Entity.Bill;

public interface BillRepo extends JpaRepository<Bill, Integer> {

}

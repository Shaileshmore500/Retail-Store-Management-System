package com.sbs.SmartBillingSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.SmartBillingSystem.Entity.Challan;
import com.sbs.SmartBillingSystem.Entity.Product;

public interface ChallanRepo extends JpaRepository<Challan, Integer> {

}

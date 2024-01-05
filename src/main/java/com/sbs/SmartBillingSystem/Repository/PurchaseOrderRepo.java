package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.SmartBillingSystem.Entity.PurchaseOrder;

public interface PurchaseOrderRepo extends JpaRepository<PurchaseOrder,Integer> {

    
}

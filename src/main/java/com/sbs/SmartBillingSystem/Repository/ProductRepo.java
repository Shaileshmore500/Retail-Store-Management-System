package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sbs.SmartBillingSystem.Entity.Challan;
import com.sbs.SmartBillingSystem.Entity.Product;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {

    // List<Product> findByChallan_fid(Challan challan_fid);
    @Query(value = "SELECT * FROM Product p WHERE p.challan_fid_party_challan_pid = :challanId", nativeQuery = true)
    List<Product> findByChallan_fid(int challanId);


}

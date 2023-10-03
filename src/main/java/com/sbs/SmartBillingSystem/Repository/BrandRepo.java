package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.SmartBillingSystem.Entity.Branch;
import com.sbs.SmartBillingSystem.Entity.Brand;

public interface BrandRepo extends JpaRepository<Brand, Integer> {

    // void save(Brand brand);

}

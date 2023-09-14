package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.sbs.SmartBillingSystem.Entity.Designation;

public interface DesignationRepo extends JpaRepository<Designation, Integer> {

}

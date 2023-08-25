package com.sbs.SmartBillingSystem.Repository;

import com.sbs.SmartBillingSystem.Entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface BranchRepo extends JpaRepository<Branch, Integer> {

}

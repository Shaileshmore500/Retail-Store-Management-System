package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sbs.SmartBillingSystem.Entity.Category;

@Repository
public interface CategoryRepo extends JpaRepository<Category, Integer> {

}
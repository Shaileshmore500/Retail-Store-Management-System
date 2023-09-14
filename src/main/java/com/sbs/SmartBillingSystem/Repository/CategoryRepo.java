package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.SmartBillingSystem.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

}

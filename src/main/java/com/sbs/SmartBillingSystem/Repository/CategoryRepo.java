package com.sbs.SmartBillingSystem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sbs.SmartBillingSystem.Entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {

    // List<Category> getAllCategory();
    // @Query("select c form Category where email=:email")
    // // public User GetUserByUSerName(@Param("email") String email);

}

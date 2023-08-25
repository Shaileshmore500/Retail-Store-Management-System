package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbs.SmartBillingSystem.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    // @Query("select u form User where email=:email")
    // public User GetUserByUSerName(@Param("email") String email);

}

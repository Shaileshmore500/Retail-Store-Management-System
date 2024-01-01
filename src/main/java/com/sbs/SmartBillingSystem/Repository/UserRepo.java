package com.sbs.SmartBillingSystem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sbs.SmartBillingSystem.Entity.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
//    @Query("select u from User where u.name=:name")
//    public User GetUserByUserName(@Param("name") String 
@Query("select u from User u where u.email = :email")
	public User getUserByUserName(@Param("email") String email);

}

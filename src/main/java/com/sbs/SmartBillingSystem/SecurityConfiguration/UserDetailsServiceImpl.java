package com.sbs.SmartBillingSystem.SecurityConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sbs.SmartBillingSystem.Entity.User;
import com.sbs.SmartBillingSystem.Repository.UserRepo;

public class UserDetailsServiceImpl implements UserDetailsService{

    @Autowired
    UserRepo repo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        User user=repo.getUserByUserName(username);
        if(user==null) {
            throw new UsernameNotFoundException("Could Not Found User");
        }
        CustomUserDetails customUserDetails;
        customUserDetails = new CustomUserDetails(user);

        
        
        return customUserDetails;
    }

    
}

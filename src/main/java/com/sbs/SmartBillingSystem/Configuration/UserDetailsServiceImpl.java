// package com.sbs.SmartBillingSystem.Configuration;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.security.core.userdetails.UserDetailsService;
// import
// org.springframework.security.core.userdetails.UsernameNotFoundException;

// public class UserDetailsServiceImpl implements UserDetailsService {

// @Autowired
// private UserRepo userRepo;

// @Override
// public UserDetails loadUserByUsername(String username) throws
// UsernameNotFoundException {

// // fetch user from database;
// User user = userRepo.GetUserByUSerName(username);

// if (user == null)
// throw new UsernameNotFoundException("Could Not Found User...");

// CustomUserDetails customUserDetails = new CustomUserDetails(user);

// return customUserDetails;
// }

// }

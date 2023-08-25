package com.sbs.SmartBillingSystem.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity

public class MyConfig {

    // @Bean
    // public UserDetailsService getUserDetailsService() {
    // return new UserDetailsServiceImpl();
    // }

    // @Bean
    // public BCryptPasswordEncoder bCryptPasswordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    // @Bean
    // public DaoAuthenticationProvider daoauthenticationProvider() {
    // DaoAuthenticationProvider authenticationProvider = new
    // DaoAuthenticationProvider();
    // authenticationProvider.setUserDetailsService(this.getUserDetailsService());
    // authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
    // return authenticationProvider;
    // }
    // configure method

}

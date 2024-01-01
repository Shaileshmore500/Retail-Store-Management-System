package com.sbs.SmartBillingSystem.SecurityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration


@EnableWebSecurity
public class MyConfigSetting extends WebSecurityConfiguration
{

    @Bean
    public UserDetailsService getUserDetailsService() {
    return new UserDetailsServiceImpl();
    }

    @Bean
    public static BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
    }

//    @Bean
//    public DaoAuthenticationProvider daoauthenticationProvider() {
//    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
//    authenticationProvider.setUserDetailsService(this.getUserDetailsService());
//    authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
//    return authenticationProvider;
//    }
    public static DaoAuthenticationProvider daoauthenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
        return authenticationProvider;
    }
    // configure method...........................................



}

package com.sbs.SmartBillingSystem.SecurityConfiguration;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurtyConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/home")
                .hasRole("ADMIN")
                .requestMatchers("/login", "/signup", "/css/nav.css", "/css/form.css", "/master/registerUser")

                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/home") // Add this line
                .permitAll();
        // http.csrf().disable()
        // .authorizeRequests()
        // .antMatchers("/login", "/signup").permitAll() // URLs allowed without
        // authentication
        // .antMatchers("/home").hasRole("ADMIN") // URL that requires ADMIN role
        // .anyRequest().authenticated() // All other URLs require authentication
        // .and()
        // .formLogin()
        // .loginPage("/login")
        // .permitAll();

        return http.build();

    }

    // @Bean
    // public UserDetailsService userDetailsService() {

    // UserDetails u1 =
    // org.springframework.security.core.userdetails.User.withUsername("shailesh")
    // .password(bCryptPasswordEncoder().encode("123"))
    // .roles("ADMIN")
    // .build();
    // UserDetails u2 =
    // org.springframework.security.core.userdetails.User.withUsername("rahul")
    // .password(bCryptPasswordEncoder().encode("123"))
    // .roles("NOR")
    // .build();

    // return new InMemoryUserDetailsManager(u1, u2);
    // }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

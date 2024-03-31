package com.sbs.SmartBillingSystem.SecurityConfiguration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authorization.AuthorityReactiveAuthorizationManager;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebSecurity
public class SecurtyConfig {





     @Bean
     public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http.csrf().disable()
                 .authorizeHttpRequests()
                 .requestMatchers("/saveuser","/register","/css/**")
                 .permitAll()
                 
                 .anyRequest()
                 .authenticated()
                 .and()
                 .formLogin()
                 .loginPage("/login")
                 .defaultSuccessUrl("/home")
                 .permitAll()
                 .loginProcessingUrl("/login")
                
                 .defaultSuccessUrl("/", true)
                
                 .failureUrl("/login?error");
                 return http.build();

           

        
         // >>>>>>> c27fea65b31e189313c5d87add41db0a721f776f
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

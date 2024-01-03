// package com.sbs.SmartBillingSystem.Configuration;

// import java.util.Collection;
// import java.util.List;

// import org.apache.catalina.User;
// import org.springframework.security.core.GrantedAuthority;
// import org.springframework.security.core.authority.SimpleGrantedAuthority;
// import org.springframework.security.core.userdetails.UserDetails;

// public class CustomUserDetails implements UserDetails {
// private com.sbs.SmartBillingSystem.Entity.User user;

// public CustomUserDetails(com.sbs.SmartBillingSystem.Entity.User user) {
// this.user = user;
// }

// @Override
// public Collection<? extends GrantedAuthority> getAuthorities() {

// SimpleGrantedAuthority simpleGrantedAuthority = new
// SimpleGrantedAuthority(user.getRole().toString());

// return List.of(simpleGrantedAuthority);
// }

// @Override
// public String getPassword() {
// // TODO Auto-generated method stub
// return user.getPassword();
// }

// @Override
// public String getUsername() {
// // TODO Auto-generated method stub
// return user.getEmail();
// }

// @Override
// public boolean isAccountNonExpired() {
// // TODO Auto-generated method stub
// return true;
// }

// @Override
// public boolean isAccountNonLocked() {
// // TODO Auto-generated method stub
// return true;
// }

// @Override
// public boolean isCredentialsNonExpired() {
// // TODO Auto-generated method stub
// return true;
// }

// @Override
// public boolean isEnabled() {
// // TODO Auto-generated method stub
// return true;
// }

// }

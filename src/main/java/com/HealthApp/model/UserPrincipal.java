//package com.HealthApp.model;
//
//import org.jspecify.annotations.Nullable;
////import org.springframework.security.core.GrantedAuthority;
////import org.springframework.security.core.authority.SimpleGrantedAuthority;
////import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.List;
//
//public class UserPrincipal implements UserDetails {
//
//    private Person person;
//
//    public UserPrincipal(Person person) {
//        this.person = person;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singleton(new SimpleGrantedAuthority(person.getRole().toString()));
//    }
//
//    @Override
//    public @Nullable String getPassword() {
//        return person.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return person.getEmail();
//    }
//}

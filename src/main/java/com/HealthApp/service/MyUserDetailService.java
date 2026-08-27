//package com.HealthApp.service;
//
//import com.HealthApp.model.Person;
//import com.HealthApp.model.UserPrincipal;
//import com.HealthApp.repo.ClientRepository;
//import com.HealthApp.repo.CoachRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class MyUserDetailService implements UserDetailsService {
//
//    @Autowired
//    private CoachRepository coachRepo;
//
//    @Autowired
//    private ClientRepository clientRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//
//        Person person = clientRepo.findByEmail(username);
//        if (person == null) {
//            person = coachRepo.findByEmail(username);
//        }
//        if (person == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new UserPrincipal(person);
//    }
//}

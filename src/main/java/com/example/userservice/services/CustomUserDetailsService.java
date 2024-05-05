package com.example.userservice.services;

import com.example.userservice.security.models.CustomUserDetails;
import com.example.userservice.models.User;
import com.example.userservice.repositories.UserRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    private UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo){
        this.userRepo = userRepo;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepo.findByEmail(email);
        if(user.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        UserDetails userDetails = new CustomUserDetails(user.get());

        return null;
    }
}

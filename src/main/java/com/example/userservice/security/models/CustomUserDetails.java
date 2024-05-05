package com.example.userservice.security.models;

import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import com.example.userservice.services.CustomUserDetailsService;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {
    private User user;

    public CustomUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<CustomGrantedAuthority> grantedAuthorities = new LinkedList<>();

        for(Role role : user.getRoles()){
            grantedAuthorities.add(new CustomGrantedAuthority(role));
        }
        return null;
    }

    @Override
    public String getPassword() {
        return user.getHashedPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

package com.example.userservice.security.models;


import com.example.userservice.models.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.NoArgsConstructor;

@JsonDeserialize
@NoArgsConstructor
public class CustomGrantedAuthority {

    private String authority;

    Role role;

    public CustomGrantedAuthority(Role role) {

        this.role = role;
    }



    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String toString() {
        return "CustomGrantedAuthority{" +
                "authority='" + authority + '\'' +
                '}';
    }
}

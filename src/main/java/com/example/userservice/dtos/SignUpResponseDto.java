package com.example.userservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SignUpResponseDto {

    private String email;
    private String name;
    private boolean isEmailVerified;
}

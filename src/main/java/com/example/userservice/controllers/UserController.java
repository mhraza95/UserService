package com.example.userservice.controllers;

import com.example.userservice.dtos.LoginRequestDto;
import com.example.userservice.dtos.LogoutRequestDto;
import com.example.userservice.dtos.SignUpRequestDto;
import com.example.userservice.dtos.SignUpResponseDto;
import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.services.UserService;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    public UserController (UserService userService) {

        this.userService = userService;
    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto loginRequestDto) {

        return userService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }

    @PostMapping("/signup")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto signUpRequestDto) {
        return toSignUpResponseDto(userService.signUp(signUpRequestDto.getName(), signUpRequestDto.getEmail(), signUpRequestDto.getPassword()));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto) {

        userService.logout(logoutRequestDto.getToken());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate/{token}")
    public User validateToken(@PathVariable("token") @NonNull String token) {

        return userService.validateToken(token);
    }

    public SignUpResponseDto toSignUpResponseDto(User user) {
        if (user == null) {
            return null;
        }

        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();

        signUpResponseDto.setName(user.getName());
        signUpResponseDto.setEmail(user.getEmail());
        signUpResponseDto.setEmailVerified(user.isEmailVerified());
        return signUpResponseDto;
    }


}

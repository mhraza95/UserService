package com.example.userservice.services;

import com.example.userservice.models.Token;
import com.example.userservice.models.User;
import com.example.userservice.repositories.TokenRepo;
import com.example.userservice.repositories.UserRepo;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private UserRepo userRepo;

    private TokenRepo tokenRepo;

    public UserService(BCryptPasswordEncoder bCryptPasswordEncoder, UserRepo userRepo, TokenRepo tokenRepo) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;

    }
    public User signUp(String name, String email, String password) {

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        User user1 = userRepo.save(user);
        return user1;


    }

    public Token login(String email, String password) {

        Optional<User> userOptional = userRepo.findByEmail(email);

        if (userOptional.isEmpty()) {

            return null;
        }

        User user = userOptional.get();
        if (bCryptPasswordEncoder.matches(password, user.getHashedPassword())) {

            Token token = new Token();
            token.setUser(user);
            token.setValue(RandomStringUtils.randomAlphanumeric(128));
            LocalDate today = LocalDate.now();
            LocalDate oneDayLater = today.plusDays(1);
            Date expiryAt = Date.from(oneDayLater.atStartOfDay(ZoneId.systemDefault()).toInstant());
            token.setExpiryAt(expiryAt);

            return tokenRepo.save(token);
        }

        return null;
    }

    public void logout(String token) {

        Optional<Token> token1 = tokenRepo.findByValueAndDeletedEquals(token, false);
        if(token1.isEmpty()) {

        }

        Token token2 = token1.get();
        token2.setDeleted(true);
        tokenRepo.save(token2);
    }

    public User validateToken(String token) {
        Optional<Token> token1 = tokenRepo.findByValueAndDeletedEquals(token, false);
        if(token1.isEmpty()) {
            return null;
        }
        Token token2 = token1.get();

        if (token2.getExpiryAt().before(new Date())) {
            return null;
        }

        return token2.getUser();
    }

}

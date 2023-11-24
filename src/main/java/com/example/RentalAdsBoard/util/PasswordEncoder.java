package com.example.RentalAdsBoard.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {
    private final BCryptPasswordEncoder INSTANCE = new BCryptPasswordEncoder();
    public BCryptPasswordEncoder passwordEncoder() {
        return INSTANCE;
    }
    public String encodePassword(String password){
        return INSTANCE.encode(password);
    }
    public boolean matchPassword(String inputPassword,String truePassword){
        return INSTANCE.matches(inputPassword,truePassword);
    }
}


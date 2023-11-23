package com.example.RentalAdsBoard.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoder {
    private static final BCryptPasswordEncoder INSTANCE = new BCryptPasswordEncoder();
    @Bean
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


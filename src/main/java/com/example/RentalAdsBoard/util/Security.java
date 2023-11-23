package com.example.RentalAdsBoard.util;

import org.aspectj.weaver.bcel.BcelAnnotation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class Security {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return bCryptPasswordEncoder;
    }
    public static String encodePassword(String password){
        return bCryptPasswordEncoder.encode(password);
    }
    public static boolean matchPassword(String inputPassword,String truePassword){
        return bCryptPasswordEncoder.matches(inputPassword,truePassword);
    }
}


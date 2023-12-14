package com.example.RentalAdsBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.io.File;


@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RentalAdsBoardApplication {
    public static void main(String[] args) {
        System.setProperty("projectPath", System.getProperty("user.dir"));
        SpringApplication.run(RentalAdsBoardApplication.class, args);

    }

}

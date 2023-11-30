package com.example.RentalAdsBoard.vo;

import lombok.Data;

@Data
public class RegisterVo {
    String username;
    String password;
    String avatarBase64;
    String role;
    String email;
}

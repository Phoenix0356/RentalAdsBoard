package com.example.RentalAdsBoard.vo;

import lombok.Data;

@Data
public class UserVo {
    Integer userId;
    String username;
    String email;
    String avatarBase64;
    String newPassword;
    String originPassword;
}

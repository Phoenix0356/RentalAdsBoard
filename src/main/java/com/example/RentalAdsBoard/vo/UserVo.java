package com.example.RentalAdsBoard.vo;

import lombok.Data;

@Data
public class UserVo {
    Integer userId;
    String email;
    String newPassword;
    String originPassword;
}

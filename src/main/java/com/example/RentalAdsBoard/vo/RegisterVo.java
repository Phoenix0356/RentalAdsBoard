package com.example.RentalAdsBoard.vo;

import lombok.Data;

@Data
public class RegisterVo {
    String username;
    String password;
    String role;
    String email;
    private Integer toInteger(String s){
        return Integer.parseInt(s);
    }
}

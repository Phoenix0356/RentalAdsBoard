package com.example.RentalAdsBoard.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data

public class AdVo {
    Integer adId;
    String title;
    String address;
    String description;
    Integer userId;
}

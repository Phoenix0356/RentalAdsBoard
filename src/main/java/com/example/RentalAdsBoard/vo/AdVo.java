package com.example.RentalAdsBoard.vo;


import com.example.RentalAdsBoard.entity.Ad;
import lombok.Data;

@Data
public class AdVo {
    Integer adId;
    String username;
    String title;
    String address;
    String description;
    public AdVo(){}
    public void setAdVo(Ad ad){
        this.setTitle(ad.getTitle());
        this.setAdId(ad.getAdId());
        this.setUsername(ad.getUser().getUsername());
        this.setAddress(ad.getAddress());
        this.setDescription(ad.getDescription());
    }

}

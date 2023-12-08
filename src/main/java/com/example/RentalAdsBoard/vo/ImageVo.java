package com.example.RentalAdsBoard.vo;

import com.example.RentalAdsBoard.entity.Image;
import com.example.RentalAdsBoard.util.DataUtil;
import lombok.Data;

@Data
public class ImageVo {
    Integer imageId;
    Integer adId;
    String imageBase64;

    public void setImageVo(Image image){
        this.setAdId(image.getAdId());
        this.setImageId(image.getImageId());
        this.setImageBase64(DataUtil.imageToBase64(image.getPath()));
    }

}

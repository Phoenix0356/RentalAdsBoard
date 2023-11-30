package com.example.RentalAdsBoard.vo;

import com.example.RentalAdsBoard.entity.Picture;
import com.example.RentalAdsBoard.util.DataUtil;
import lombok.Data;

@Data
public class PictureVo {
    Integer pictureId;
    Integer adId;
    String pictureBase64;

    public void setPictureVo(Picture picture){
        this.setAdId(picture.getAdId());
        this.setPictureId(picture.getPictureId());
        this.setPictureBase64(DataUtil.pictureToBase64(picture.getPath()));
    }

}

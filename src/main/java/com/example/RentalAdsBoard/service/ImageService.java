package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.vo.ImageVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {

    ResultVo getImageById(Integer imageId);

    ResultVo getAdFirstImage(Integer adId);

    ResultVo getAdImageList(Integer adId);


    ResultVo saveImageById(ImageVo imageVo);

    ResultVo updateImageById(ImageVo imageVo);

    ResultVo deleteImageById(Integer imageId);
}

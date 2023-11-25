package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface PictureService {

    ResultVo getPictureById(Integer pictureId);

    ResultVo getAdPictureList(Integer adId);

    ResultVo deletePictureById(Integer pictureId);
}

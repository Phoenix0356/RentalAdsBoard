package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.vo.PictureVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface PictureService {

    ResultVo getPictureById(Integer pictureId);

    ResultVo getAdFirstPicture(Integer adId);

    ResultVo getAdPictureList(Integer adId);


    ResultVo savePictureById(PictureVo pictureVo);

    ResultVo updatePictureById(PictureVo pictureVo);

    ResultVo deletePictureById(Integer pictureId);
}

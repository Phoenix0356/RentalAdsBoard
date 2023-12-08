package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.vo.PictureVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface PictureService {

    ResultVo getPictureById(Integer pictureId) throws DataBaseException;

    ResultVo getAdFirstPicture(Integer adId) throws DataBaseException;

    ResultVo getAdPictureList(Integer adId) throws DataBaseException;


    ResultVo savePictureById(PictureVo pictureVo) throws DataBaseException;

    ResultVo updatePictureById(PictureVo pictureVo) throws DataBaseException;

    ResultVo deletePictureById(Integer pictureId) throws DataBaseException;
}

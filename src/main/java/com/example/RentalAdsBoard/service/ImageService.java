package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.vo.ImageVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface ImageService {

    ResultVo getImageById(Integer imageId) throws DataBaseException;

    ResultVo getAdFirstImage(Integer adId) throws DataBaseException;

    ResultVo getAdImageList(Integer adId) throws DataBaseException;


    ResultVo saveImageById(ImageVo imageVo) throws DataBaseException;

    ResultVo updateImageById(ImageVo imageVo) throws DataBaseException;

    ResultVo deleteImageById(Integer imageId) throws DataBaseException;
}

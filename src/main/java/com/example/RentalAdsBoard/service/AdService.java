package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.vo.AdVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface AdService {
    ResultVo getUserAdList(Integer userId,Integer pageNumber,Integer size);

    ResultVo getAdsFromIndex(Integer pageNumber, Integer size) throws DataBaseException;

    ResultVo getAdById(Integer adId) throws DataBaseException;

    ResultVo SaveAdById(Integer userId,AdVo adVo) throws DataBaseException;

    ResultVo updateAdById(Integer userId,AdVo adVo) throws DataBaseException;

    ResultVo deleteAdById(Integer adId) throws DataBaseException;
}

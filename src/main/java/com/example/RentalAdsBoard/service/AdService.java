package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.vo.AdVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface AdService {
    ResultVo getUserAdList(Integer userId);

    ResultVo getLatestAdList();

    ResultVo getAdsFromIndex(int startNumber, int adsNumber);

    ResultVo getAdById(Integer adId);

    ResultVo SaveAdById(AdVo adVo);

    ResultVo updateAdById(AdVo adVo);

    ResultVo deleteAdById(Integer adId);
}

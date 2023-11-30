package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.vo.AdVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.stereotype.Service;

@Service
public interface AdService {
    ResultVo getUserAdList(Integer userId);

    ResultVo getLatestAdList();

    ResultVo getAdById(Integer adId);

    ResultVo SaveOrUpdateAd(AdVo adVo);

    ResultVo deleteAdById(Integer adId);
}

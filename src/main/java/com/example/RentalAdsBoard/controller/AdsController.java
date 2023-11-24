package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.service.AdService;
import com.example.RentalAdsBoard.vo.AdVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdsController {
    @Autowired
    AdService adService;
    @GetMapping("/ads/home")
    public ResultVo getLatestAdsList(@RequestParam("batch_size")Integer batchSize){
        return adService.getLatestAdList(batchSize);
    }
    @GetMapping("/ads/get")
    public ResultVo getUserAds(@RequestParam("user_id") Integer userId){
        return adService.getUserAdList(userId);
    }
    @GetMapping("/ads/user/get")
    public ResultVo getAdById(@RequestParam("ad_id")Integer adId){
        return adService.getAdById(adId);
    }

    @PostMapping("/ads/save")
    public ResultVo saveAd(@RequestBody() AdVo adVo ){
        return adService.SaveOrUpdateAd(adVo);
    }

    @PutMapping("/ads/update")
    public ResultVo updateAd(@RequestBody() AdVo adVo ){
        return adService.SaveOrUpdateAd(adVo);
    }

    @DeleteMapping("/ads/delete")
    public ResultVo deleteAd(@RequestParam("ad_id")Integer adId){
        return adService.deleteAdById(adId);
    }

}

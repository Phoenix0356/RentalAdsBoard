package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.service.AdService;
import com.example.RentalAdsBoard.vo.AdVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AdsController {
    @Autowired
    AdService adService;
    //get ads for home page
    @GetMapping("/ads/list")
    public ResultVo getLatestAdsList(){
        return adService.getLatestAdList();
    }
    //get ads owned by the user
    @GetMapping("/ads/list/self")
    public ResultVo getUserAds(HttpServletRequest request){
        Integer userId=(Integer) request.getAttribute("userId");
        return adService.getUserAdList(userId);
    }
    //get one ad by adId
    @GetMapping("/ads/get")
    public ResultVo getAdById(@RequestParam("ad_id")Integer adId){
        return adService.getAdById(adId);
    }
    @GetMapping("/ads/get/index")
    public ResultVo getAdFromIndex(@RequestParam("start_number")Integer startNumber,
                                   @RequestParam("ads_number")Integer adsNumber){
        return adService.getAdsFromIndex(startNumber,adsNumber);
    }
    @PostMapping("/ads/save")
    public ResultVo saveAd(HttpServletRequest request,
                           @RequestBody() AdVo adVo ){
        Integer userId=(Integer) request.getAttribute("userId");
        return adService.SaveAdById(userId,adVo);
    }

    @PostMapping("/ads/update")
    public ResultVo updateAd(HttpServletRequest request,
                             @RequestBody() AdVo adVo ){
        Integer userId=(Integer) request.getAttribute("userId");
        return adService.updateAdById(userId,adVo);
    }

//    @PutMapping("/ads/update")
//    public ResultVo updateAd(@RequestBody() AdVo adVo ){
//        return adService.SaveOrUpdateAd(adVo);
//    }

    @DeleteMapping("/ads/delete")
    public ResultVo deleteAd(@RequestParam("ad_id")Integer adId){
        return adService.deleteAdById(adId);
    }

}

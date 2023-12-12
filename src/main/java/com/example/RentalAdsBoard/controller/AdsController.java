package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.service.AdService;
import com.example.RentalAdsBoard.vo.AdVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AdsController extends BaseController{
    @Autowired
    AdService adService;

    @GetMapping("/ads/get")
    public ResultVo getUserAds(@ModelAttribute("userId") Integer userId,
                               @RequestParam("page_number")Integer page_number,
                               @RequestParam("size")Integer size){
        return adService.getUserAdList(userId,page_number,size);
    }
    @GetMapping("/ads/user/get")
    public ResultVo getAdById(@RequestParam("ad_id")Integer adId){
        return adService.getAdById(adId);
    }
    @GetMapping("/ads/index/get")
    public ResultVo getAdFromIndex(@RequestParam("page_number")Integer page,
                                   @RequestParam("size")Integer size){
        return adService.getAdsFromIndex(page,size);
    }
    @PostMapping("/ads/save")
    public ResultVo saveAd(@ModelAttribute("userId") Integer userId,
                           @RequestBody() AdVo adVo ){

        return adService.SaveAdById(userId,adVo);
    }

    @PostMapping("/ads/update")
    public ResultVo updateAd(@ModelAttribute("userId") Integer userId,
                             @RequestBody() AdVo adVo ){

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

    @GetMapping("/ads/search")
    public  ResultVo searchAds(@RequestParam("Key")String Key,
                               @RequestParam("page_number") Integer pageNumber,
                               @RequestParam("size") Integer size){
        return adService.searchAdsByKey(Key,pageNumber,size);
    }

}

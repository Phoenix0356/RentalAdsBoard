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
    @GetMapping("/ads/list/self")
    public ResultVo getUserAds(HttpServletRequest request,
                               @RequestParam("page_number")Integer page_number,
                               @RequestParam("size")Integer size){
        Integer userId=(Integer) request.getAttribute("userId");
        return adService.getUserAdList(userId,page_number,size);
    }
    @GetMapping("/ads/get")
    public ResultVo getAdById(@RequestParam("ad_id")Integer adId){
        return adService.getAdById(adId);
    }
    @GetMapping("/ads/list")
    public ResultVo getAdFromIndex(@RequestParam("page_number") Integer pageNumber,
                                   @RequestParam("size") Integer size){
        return adService.getAdsFromIndex(pageNumber,size);
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

    @DeleteMapping("/ads/delete")
    public ResultVo deleteAd(@RequestParam("ad_id")Integer adId){
        return adService.deleteAdById(adId);
    }

}
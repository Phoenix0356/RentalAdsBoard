package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
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
    @GetMapping("/ads/get")
    public ResultVo getUserAds(HttpServletRequest request,
                               @RequestParam("page_number")Integer page_number,
                               @RequestParam("size")Integer size){
        Integer userId=(Integer) request.getAttribute("userId");
        return adService.getUserAdList(userId,page_number,size);
    }
    //get one ad by adId
    @GetMapping("/ads/user/get")
    public ResultVo getAdById(@RequestParam("ad_id")Integer adId) throws DataBaseException {
        return adService.getAdById(adId);
    }
    
    @GetMapping("/ads/index/get")
    public ResultVo getAdFromIndex(@RequestParam("page_number") Integer pageNumber,
                                   @RequestParam("size") Integer size) throws DataBaseException {
        return adService.getAdsFromIndex(pageNumber,size);
    }
    @PostMapping("/ads/save")
    public ResultVo saveAd(HttpServletRequest request,
                           @RequestBody() AdVo adVo ) throws DataBaseException {
        Integer userId=(Integer) request.getAttribute("userId");
        return adService.SaveAdById(userId,adVo);
    }

    @PutMapping("/ads/update")
    public ResultVo updateAd(HttpServletRequest request,
                             @RequestBody() AdVo adVo ) throws DataBaseException {
        Integer userId=(Integer) request.getAttribute("userId");
        return adService.updateAdById(userId,adVo);
    }

    @DeleteMapping("/ads/delete")
    public ResultVo deleteAd(@RequestParam("ad_id")Integer adId) throws DataBaseException {
        return adService.deleteAdById(adId);
    }
    
    @GetMapping("/ads/search")
    public  ResultVo searchAds(@RequestParam("Key")String Key,
                               @RequestParam("page_number") Integer pageNumber,
                               @RequestParam("size") Integer size) throws DataBaseException{
        return adService.searchAdsByKey(Key,pageNumber,size);
    }
}

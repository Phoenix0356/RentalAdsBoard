package com.example.RentalAdsBoard.controller;
import com.example.RentalAdsBoard.service.PictureService;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class PictureController {
    @Autowired
    PictureService pictureService;

    @GetMapping("/picture/get")
    public ResultVo getPictureById(@RequestParam("picture_id")Integer pictureId){
        return pictureService.getPictureById(pictureId);
    }
    //获取用户主页广告的第一张图片
    @GetMapping("/picture/get/first")
    public ResultVo getAdFirstPicture(@RequestParam("ad_id")Integer adId){
        return pictureService.getAdFirstPicture(adId);
    }
    @GetMapping("/picture/list")
    public ResultVo getPictureListById(@RequestParam("ad_id") Integer adId){
        return pictureService.getAdPictureList(adId);
    }

    @DeleteMapping("/picture/delete")
    public ResultVo deletePictureById(@RequestParam("picture_id") Integer pictureId){
        return pictureService.deletePictureById(pictureId);
    }

}

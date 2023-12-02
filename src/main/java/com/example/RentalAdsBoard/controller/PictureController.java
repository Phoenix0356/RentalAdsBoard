package com.example.RentalAdsBoard.controller;
import com.example.RentalAdsBoard.service.PictureService;
import com.example.RentalAdsBoard.vo.PictureVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/picture/save")
    public ResultVo updatePicture(@RequestBody PictureVo pictureVo){
        return pictureService.savePictureById(pictureVo);
    }

    @PostMapping("/picture/update")
    public ResultVo savePicture(@RequestBody PictureVo pictureVo){
        return pictureService.updatePictureById(pictureVo);
    }

    @DeleteMapping("/picture/delete")
    public ResultVo deletePictureById(@RequestParam("picture_id") Integer pictureId){
        return pictureService.deletePictureById(pictureId);
    }

}

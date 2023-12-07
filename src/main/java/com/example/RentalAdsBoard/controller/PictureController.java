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
    // get a picture by pictureId
    @GetMapping("/picture/get")
    public ResultVo getPictureById(@RequestParam("picture_id")Integer pictureId){
        return pictureService.getPictureById(pictureId);
    }
    // get the first picture of the ad
    @GetMapping("/picture/ad/first")
    public ResultVo getAdFirstPicture(@RequestParam("ad_id")Integer adId){
        return pictureService.getAdFirstPicture(adId);
    }
    // get all pictures list of the ad
    @GetMapping("/picture/ad/list")
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

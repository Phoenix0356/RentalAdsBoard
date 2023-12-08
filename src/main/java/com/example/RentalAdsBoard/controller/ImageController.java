package com.example.RentalAdsBoard.controller;
import com.example.RentalAdsBoard.service.ImageService;
import com.example.RentalAdsBoard.vo.ImageVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ImageController {
    @Autowired
    ImageService imageService;
    // get a image by imageId
    @GetMapping("/image/get")
    public ResultVo getImageById(@RequestParam("image_id")Integer imageId){
        return imageService.getImageById(imageId);
    }
    // get the first image of the ad
    @GetMapping("/image/ad/first")
    public ResultVo getAdFirstImage(@RequestParam("ad_id")Integer adId){
        return imageService.getAdFirstImage(adId);
    }
    // get all images list of the ad
    @GetMapping("/image/ad/list")
    public ResultVo getImageListById(@RequestParam("ad_id") Integer adId){
        return imageService.getAdImageList(adId);
    }
    @PostMapping("/image/save")
    public ResultVo updateImage(@RequestBody ImageVo imageVo){
        return imageService.saveImageById(imageVo);
    }

    @PostMapping("/image/update")
    public ResultVo saveImage(@RequestBody ImageVo imageVo){
        return imageService.updateImageById(imageVo);
    }

    @DeleteMapping("/image/delete")
    public ResultVo deleteImageById(@RequestParam("image_id") Integer imageId){
        return imageService.deleteImageById(imageId);
    }

}

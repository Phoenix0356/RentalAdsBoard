package com.example.RentalAdsBoard.controller;
import com.example.RentalAdsBoard.controller.exception.DataBaseException;
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
    public ResultVo getImageById(@RequestParam("image_id")Integer imageId) throws DataBaseException {
        return imageService.getImageById(imageId);
    }
    // get the first image of the ad
    @GetMapping("/image/ad/first")
    public ResultVo getAdFirstImage(@RequestParam("ad_id")Integer adId) throws DataBaseException {
        return imageService.getAdFirstImage(adId);
    }
    // get all images list of the ad
    @GetMapping("/image/ad/list")
    public ResultVo getImageListById(@RequestParam("ad_id") Integer adId) throws DataBaseException {
        return imageService.getAdImageList(adId);
    }
    @PostMapping("/image/save")
    public ResultVo updateImage(@RequestBody ImageVo imageVo) throws DataBaseException {
        return imageService.saveImageById(imageVo);
    }

    @PostMapping("/image/update")
    public ResultVo saveImage(@RequestBody ImageVo imageVo) throws DataBaseException {
        return imageService.updateImageById(imageVo);
    }

    @DeleteMapping("/image/delete")
    public ResultVo deleteImageById(@RequestParam("image_id") Integer imageId) throws DataBaseException {
        return imageService.deleteImageById(imageId);
    }

}

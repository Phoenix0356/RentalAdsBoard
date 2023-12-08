package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.dao.AdDao;
import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.ImageDao;
import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.entity.Image;
import com.example.RentalAdsBoard.service.ImageService;
import com.example.RentalAdsBoard.util.DataUtil;
import com.example.RentalAdsBoard.vo.ImageVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageDao imageDao;
    @Autowired
    private BaseDao<Image> baseDao;
    @Autowired
    private AdDao adDao;
    @Override
    public ResultVo getImageById(Integer imageId) throws DataBaseException {
        ImageVo imageVo = new ImageVo();
        try {
            Image image = imageDao.getImageById(imageId);
            imageVo.setImageVo(image);
        }catch (Exception e){
            throw new DataBaseException("Failed loading image");
        }
        return new ResultVo().success(imageVo);
    }
    @Override
    public ResultVo getAdFirstImage(Integer adId) throws DataBaseException {
        ImageVo imageVo = new ImageVo();
        try {

            Image image = imageDao.getAdFirstImage(adId);
            if (image == null) return new ResultVo().success();

            imageVo.setImageVo(image);

        }catch (Exception e){
            throw new DataBaseException("Failed loading image");
        }
        return new ResultVo().success(imageVo);
    }
    @Override
    public ResultVo getAdImageList(Integer adId) throws DataBaseException {

        List<ImageVo> listVo = new ArrayList<>();
        try {
            List<Image> list = imageDao.getAdImageList(adId);

            for (Image image : list){
                ImageVo imageVo = new ImageVo();
                imageVo.setImageVo(image);
                listVo.add(imageVo);
            }

        }catch (Exception e){
            throw new DataBaseException("Failed loading images");
        }
        return new ResultVo().success(listVo);
    }

    @Override
    public ResultVo saveImageById(ImageVo imageVo) throws DataBaseException {
        try {
            Image image=new Image();

            Ad ad=adDao.getById(imageVo.getAdId());
            image.setAd(ad);

            image.setPath(DataUtil.saveOrUpdateImage(imageVo.getImageBase64(), image.getPath(), "static\\itemImage",false));

            int imageId = baseDao.save(image);
            imageVo.setImageId(imageId);

        }catch (Exception e){
            throw new DataBaseException("Failed saving image");
        }
        return new ResultVo().success(imageVo);
    }

    @Override
    public ResultVo updateImageById(ImageVo imageVo) throws DataBaseException {
        try {
            Image image=imageDao.getImageById(imageVo.getImageId());


            Ad ad=adDao.getById(imageVo.getAdId());
            image.setAd(ad);
            image.setPath(DataUtil.saveOrUpdateImage(imageVo.getImageBase64(), image.getPath(), "static\\itemImage",false));

            baseDao.update(image);

        }catch (Exception e){
            throw new DataBaseException("Failed updating image");
        }
        return new ResultVo().success(imageVo);
    }

    @Override
    public ResultVo deleteImageById(Integer imageId) throws DataBaseException {
        try {
            baseDao.delete(imageDao.getImageById(imageId));
        }catch (Exception e){
            throw new DataBaseException("Failed deleting image");
        }
        return new ResultVo().success();
    }
}

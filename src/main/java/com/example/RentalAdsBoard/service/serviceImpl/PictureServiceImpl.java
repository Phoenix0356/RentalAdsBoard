package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.PictureDao;
import com.example.RentalAdsBoard.entity.Picture;
import com.example.RentalAdsBoard.service.PictureService;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    PictureDao pictureDao;
    @Autowired
    BaseDao<Picture> baseDao;
    @Override
    public ResultVo getPictureById(Integer pictureId){
        Picture picture;
        try {
            picture=pictureDao.getPictureById(pictureId);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(picture);
    }
    @Override
    public ResultVo getAdPictureList(Integer adId){
        List<Picture> list;
        try {
            list=pictureDao.getAdPictureList(adId);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success();
    }

    @Override
    public ResultVo deletePictureById(Integer pictureId){
        try {
            baseDao.delete(pictureDao.getPictureById(pictureId));
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success();
    }
}

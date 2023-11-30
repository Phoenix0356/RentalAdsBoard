package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.AdDao;
import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.PictureDao;
import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.entity.Picture;
import com.example.RentalAdsBoard.service.PictureService;
import com.example.RentalAdsBoard.util.DataUtil;
import com.example.RentalAdsBoard.vo.PictureVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private BaseDao<Picture> baseDao;
    @Autowired
    private AdDao adDao;
    @Autowired
    private DataUtil dataUtil;
    @Value("${picture_storage.path}")
    private String path;
    @Override
    public ResultVo getPictureById(Integer pictureId){
        PictureVo pictureVo = new PictureVo();
        try {
            Picture picture=pictureDao.getPictureById(pictureId);
            pictureVo.setPictureBase64(dataUtil.pictureToBase64(picture.getPath()));
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(pictureVo);
    }
    @Override
    public ResultVo getAdFirstPicture(Integer adId){
        PictureVo pictureVo=new PictureVo();
        try {

            Picture picture=pictureDao.getFirstPicture(adId);
            if (picture==null) return new ResultVo().success();

            pictureVo.setAdId(picture.getAdId());
            pictureVo.setPictureId(picture.getPictureId());
            pictureVo.setPictureBase64(dataUtil.pictureToBase64(picture.getPath()));

        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(pictureVo);
    }
    @Override
    public ResultVo getAdPictureList(Integer adId){
        List<Picture> list;
        List<PictureVo> listVo = new ArrayList<>();
        try {
            list=pictureDao.getAdPictureList(adId);
            for (Picture picture:list){
                PictureVo pictureVo=new PictureVo();
                pictureVo.setAdId(pictureVo.getAdId());
                pictureVo.setPictureId(picture.getPictureId());
                pictureVo.setPictureBase64(dataUtil.pictureToBase64(picture.getPath()));
                listVo.add(pictureVo);
            }

        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(listVo);
    }

    @Override
    public ResultVo savePicture(PictureVo pictureVo){
        Picture picture=new Picture();
        try {
            Ad ad=adDao.getById(pictureVo.getAdId());
            picture.setAd(ad);
            picture.setPath(dataUtil.saveOrUpdateImage(pictureVo.getPictureBase64(), picture.getPath(), path,false));
            baseDao.save(picture);
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

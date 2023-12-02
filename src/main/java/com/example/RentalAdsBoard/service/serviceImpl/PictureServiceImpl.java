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
    @Value("${picture_storage.path}")
    private String path;
    @Override
    public ResultVo getPictureById(Integer pictureId){
        PictureVo pictureVo = new PictureVo();
        try {
            Picture picture=pictureDao.getPictureById(pictureId);
            pictureVo.setPictureVo(picture);
        }catch (Exception e){
            return new ResultVo().error("get picture failed");
        }
        return new ResultVo().success(pictureVo);
    }
    @Override
    public ResultVo getAdFirstPicture(Integer adId){
        PictureVo pictureVo=new PictureVo();
        try {

            Picture picture=pictureDao.getFirstPicture(adId);
            if (picture==null) return new ResultVo().success();

            pictureVo.setPictureVo(picture);

        }catch (Exception e){
            return new ResultVo().error("load picture failed");
        }
        return new ResultVo().success(pictureVo);
    }
    @Override
    public ResultVo getAdPictureList(Integer adId){

        List<PictureVo> listVo = new ArrayList<>();
        try {
            List<Picture> list=pictureDao.getAdPictureList(adId);

            for (Picture picture:list){
                PictureVo pictureVo=new PictureVo();
                pictureVo.setPictureVo(picture);
                listVo.add(pictureVo);
            }

        }catch (Exception e){
            return new ResultVo().error("load picture list failed");
        }
        return new ResultVo().success(listVo);
    }

    @Override
    public ResultVo savePictureById(PictureVo pictureVo){
        try {
            Picture picture=new Picture();


            Ad ad=adDao.getById(pictureVo.getAdId());
            picture.setAd(ad);

            picture.setPath(DataUtil.saveOrUpdateImage(pictureVo.getPictureBase64(), picture.getPath(), path,false));

           int pictureId = baseDao.save(picture);
           pictureVo.setPictureId(pictureId);

        }catch (Exception e){
            return new ResultVo().error("save picture failed");
        }
        return new ResultVo().success(pictureVo);
    }

    @Override
    public ResultVo updatePictureById(PictureVo pictureVo){
        try {
            Picture picture=pictureDao.getPictureById(pictureVo.getPictureId());


            Ad ad=adDao.getById(pictureVo.getAdId());
            picture.setAd(ad);
            picture.setPath(DataUtil.saveOrUpdateImage(pictureVo.getPictureBase64(), picture.getPath(), path,false));

            baseDao.update(picture);

        }catch (Exception e){
            return new ResultVo().error("Update picture failed");


        }
        return new ResultVo().success(pictureVo);
    }

    @Override
    public ResultVo deletePictureById(Integer pictureId){
        try {
            baseDao.delete(pictureDao.getPictureById(pictureId));
        }catch (Exception e){
            return new ResultVo().error("delete picture failed");
        }
        return new ResultVo().success();
    }
}

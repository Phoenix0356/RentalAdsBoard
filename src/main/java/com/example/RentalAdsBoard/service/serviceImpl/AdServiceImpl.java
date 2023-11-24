package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.AdDao;
import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.service.AdService;
import com.example.RentalAdsBoard.vo.AdVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {
    @Autowired
    BaseDao<Ad> baseDao;
    @Autowired
    AdDao adDao;
    @Override
    public ResultVo getUserAdList(Integer userId){
        List<Ad> list;
        try {
            list=adDao.getUserADs(userId);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(list);
    }
    @Override
    public ResultVo getLatestAdList(Integer batchSize){
        List<Ad> list;
        try {
            list=adDao.getAdsList(batchSize);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(list);
    }
    @Override
    public ResultVo getAdById(Integer adId){
        Ad ad;
        try {
            ad=adDao.getById(adId);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(ad);
    }
    @Override
    public ResultVo SaveOrUpdateAd(AdVo adVo){
        Ad ad=new Ad();
        ad.setAddress(adVo.getAddress());
        ad.setTitle(adVo.getTitle());
        ad.setDescription(adVo.getDescription());
        ad.setUserId(adVo.getUserId());
        try {
            baseDao.save(ad);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(ad);
    }

    @Override
    public ResultVo deleteAdById(Integer adId){
        try {
            baseDao.delete(adDao.getById(adId));
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success();
    }
}

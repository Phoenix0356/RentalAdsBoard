package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.AdDao;
import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.AdService;
import com.example.RentalAdsBoard.vo.AdVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AdServiceImpl implements AdService {
    @Autowired
    BaseDao<Ad> baseDao;
    @Autowired
    AdDao adDao;
    @Autowired
    UserDao userDao;
    @Override
    public ResultVo getUserAdList(Integer userId){
        List<AdVo> adVoList=new ArrayList<>();
        try {
            List<Ad> list=adDao.getUserADs(userId);

            for (Ad ad:list){
                AdVo adVo=new AdVo();
                adVo.setAdVo(ad);
                adVoList.add(adVo);
            }

        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(adVoList);
    }
    @Override
    public ResultVo getLatestAdList(){
        List<AdVo> adVoList=new ArrayList<>();
        try {
            List<Ad> list=adDao.getAdsList();

            for (Ad ad:list){
               AdVo adVo=new AdVo();
               adVo.setAdVo(ad);
               adVoList.add(adVo);
            }
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(adVoList);
    }
    @Override
    public ResultVo getAdById(Integer adId){
        AdVo adVo;
        try {
            Ad ad=adDao.getById(adId);
            adVo=new AdVo();
            adVo.setAdVo(ad);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(adVo);
    }
    @Override
    public ResultVo SaveOrUpdateAd(AdVo adVo){
        try {
            Ad ad;
            if (adVo.getAdId()==null) ad=new Ad();
            else ad=adDao.getById(adVo.getAdId());

            ad.setAddress(adVo.getAddress());
            ad.setTitle(adVo.getTitle());
            ad.setDescription(adVo.getDescription());
            User user=userDao.getById(adVo.getUserId());
            ad.setUser(user);

            int adId=baseDao.save(ad);
            adVo.setAdId(adId);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(adVo);
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

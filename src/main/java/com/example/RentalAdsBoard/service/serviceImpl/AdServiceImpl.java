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
            return new ResultVo().error("load user ads list failed");
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
            return new ResultVo().error("load ads list failed");
        }
        return new ResultVo().success(adVoList);
    }

    @Override
    public ResultVo getAdsFromIndex(int startNumber, int adsNumber){
        List<AdVo> adVoList=new ArrayList<>();
        try {
            List<Ad> list=adDao.getAdsList();

            for (Ad ad:list){
                AdVo adVo=new AdVo();
                adVo.setAdVo(ad);
                adVoList.add(adVo);
            }
        }catch (Exception e){
            return new ResultVo().error("load ads failed");
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
            return new ResultVo().error("get ads failed");
        }
        return new ResultVo().success(adVo);
    }
    @Override
    public ResultVo SaveAdById(Integer userId,AdVo adVo){
        boolean saveFlag=true;
        try {
            Ad ad;
            if (adVo.getAdId()==null)ad =new Ad();
            else {
                saveFlag=false;
                ad=adDao.getById(adVo.getAdId());
            }

            ad.setAddress(adVo.getAddress());
            ad.setTitle(adVo.getTitle());
            ad.setDescription(adVo.getDescription());

            if (saveFlag){
                User user=userDao.getById(userId);
                ad.setUser(user);
                int adId=baseDao.save(ad);
                adVo.setAdId(adId);
            }else baseDao.update(ad);

        }catch (Exception e){
            return new ResultVo().error("save ad failed");
        }
        return new ResultVo().success(adVo);
    }
    @Override
    public ResultVo updateAdById(Integer userId,AdVo adVo){
        try {
            Ad ad=adDao.getById(adVo.getAdId());

            ad.setAddress(adVo.getAddress());
            ad.setTitle(adVo.getTitle());
            ad.setDescription(adVo.getDescription());

            baseDao.update(ad);

        }catch (Exception e){
            return new ResultVo().error("update ad failed");
        }
        return new ResultVo().success(adVo);
    }


    @Override
    public ResultVo deleteAdById(Integer adId){
        try {
            baseDao.delete(adDao.getById(adId));
        }catch (Exception e){
            return new ResultVo().error("delete ad failed");
        }
        return new ResultVo().success();
    }
}

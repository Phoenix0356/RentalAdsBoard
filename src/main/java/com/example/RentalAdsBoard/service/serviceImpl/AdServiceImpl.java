package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.AdDao;
import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.PageDao;
import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.AdService;
import com.example.RentalAdsBoard.vo.AdVo;
import com.example.RentalAdsBoard.vo.PageVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
public class AdServiceImpl implements AdService {
    @Autowired
    BaseDao<Ad> baseDao;
    @Autowired
    AdDao adDao;
    @Autowired
    UserDao userDao;
    @Autowired
    PageDao pageDao;
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
    public ResultVo getAdsFromIndex(Integer pageNumber, Integer size){
        List<AdVo> adVoList=new ArrayList<>();
        PageVo pageVo=new PageVo();
        long totalPages;
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "adId");
            Pageable pageable= PageRequest.of(pageNumber, size,sort);
            Page<Ad> page=pageDao.findAll(pageable);
            totalPages=page.getTotalElements();

            for (Ad ad:page){
                AdVo adVo=new AdVo();
                adVo.setAdVo(ad);
                adVoList.add(adVo);
            }

            pageVo.setTotalPage(totalPages);
            pageVo.setList(adVoList);
        }catch (Exception e){
            return new ResultVo().error("load ads failed");
        }
        return new ResultVo().success(pageVo);
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

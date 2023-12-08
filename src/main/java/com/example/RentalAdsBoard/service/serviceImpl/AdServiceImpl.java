package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.*;
import com.example.RentalAdsBoard.dao.pageDao.AdPageDao;
import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.AdService;
import com.example.RentalAdsBoard.util.DataUtil;
import com.example.RentalAdsBoard.vo.AdVo;
import com.example.RentalAdsBoard.vo.PageVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Autowired
    AdPageDao adpageDao;
    @Override
    public ResultVo getUserAdList(Integer userId, Integer pageNumber, Integer size){
        List<AdVo> adVoList=new ArrayList<>();
        PageVo<AdVo> pageVo=new PageVo<>();
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "adId");
            Pageable pageable=PageRequest.of(pageNumber,size,sort);
            Page<Ad> page=adpageDao.findByUserId(userId,pageable);
            long totalPage=page.getTotalPages();

            for (Ad ad:page){
                AdVo adVo=new AdVo();
                adVo.setAdVo(ad);
                adVoList.add(adVo);
            }

            pageVo.setVoList(adVoList);
            pageVo.setTotalPages(totalPage);

        }catch (Exception e){
            return new ResultVo().error("load user ads list failed");
        }
        return new ResultVo().success(pageVo);
    }

    @Override
    public ResultVo getAdsFromIndex(Integer pageNumber, Integer size){
        List<AdVo> adVoList=new ArrayList<>();
        PageVo<AdVo> pageVo=new PageVo<>();
        try {

            Sort sort = Sort.by(Sort.Direction.DESC, "adId");
            Pageable pageable= PageRequest.of(pageNumber, size,sort);
            Page<Ad> page=adpageDao.findAll(pageable);
            long totalPages=page.getTotalPages();

            for (Ad ad:page){
                AdVo adVo=new AdVo();
                adVo.setAdVo(ad);
                adVoList.add(adVo);
            }

            pageVo.setVoList(adVoList);
            pageVo.setTotalPages(totalPages);

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
        try {
            Ad ad=new Ad();

            ad.setAddress(adVo.getAddress());
            ad.setTitle(adVo.getTitle());
            ad.setDescription(adVo.getDescription());

            User user=userDao.getById(userId);
            ad.setUser(user);

            int adId=baseDao.save(ad);
            adVo.setAdId(adId);


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
            Ad ad=adDao.getById(adId);
            baseDao.delete(ad);
            DataUtil.deleteAllPictures(ad);
        }catch (Exception e){
            return new ResultVo().error("delete ad failed");
        }
        return new ResultVo().success();
    }
}

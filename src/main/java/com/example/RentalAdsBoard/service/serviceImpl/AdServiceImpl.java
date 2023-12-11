package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.dao.AdDao;
import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.dao.pageDao.AdPageDao;
import com.example.RentalAdsBoard.dao.pageDao.AdRepository;
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
    @Autowired
    private AdRepository adRepository;
    @Override
    public ResultVo getUserAdList(Integer userId, Integer pageNumber, Integer size){
        List<AdVo> adVoList=new ArrayList<>();
        PageVo<AdVo> pageVo=new PageVo<>();
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "adId");
            Pageable pageable=PageRequest.of(pageNumber,size,sort);
            Page<Ad> page=adpageDao.findByUserId(userId,pageable);
            
            setPageVo(adVoList, pageVo, page);

        }catch (Exception e){
            return new ResultVo().error("load user ads list failed");
        }
        return new ResultVo().success(pageVo);
    }
    
    //set PageVo
    private void setPageVo(List<AdVo> adVoList, PageVo<AdVo> pageVo, Page<Ad> page) {
        long totalPage=page.getTotalPages();

        for (Ad ad:page){
            AdVo adVo=new AdVo();
            adVo.setAdVo(ad);
            adVoList.add(adVo);
        }

        pageVo.setVoList(adVoList);
        pageVo.setTotalPages(totalPage);
    }

    @Override
    public ResultVo getAdsFromIndex(Integer pageNumber, Integer size) throws DataBaseException {
        List<AdVo> adVoList=new ArrayList<>();
        PageVo<AdVo> pageVo=new PageVo<>();
        try {

            Sort sort = Sort.by(Sort.Direction.DESC, "adId");
            Pageable pageable= PageRequest.of(pageNumber, size,sort);
            Page<Ad> page=adpageDao.findAll(pageable);
            setPageVo(adVoList, pageVo, page);

        }catch (Exception e){
            throw new DataBaseException("get ads failed");
        }
        return new ResultVo().success(pageVo);
    }
    @Override
    public ResultVo getAdById(Integer adId) throws DataBaseException {
        AdVo adVo;
        try {
            Ad ad=adDao.getById(adId);
            adVo=new AdVo();
            adVo.setAdVo(ad);
        }catch (Exception e){
            throw new DataBaseException("get ad failed");
        }
        return new ResultVo().success(adVo);
    }
    @Override
    public ResultVo SaveAdById(Integer userId,AdVo adVo) throws DataBaseException {
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
            throw new DataBaseException("save ad failed");
        }
        return new ResultVo().success(adVo);
    }
    @Override
    public ResultVo updateAdById(Integer userId,AdVo adVo) throws DataBaseException {
        try {
            Ad ad=adDao.getById(adVo.getAdId());

            ad.setAddress(adVo.getAddress());
            ad.setTitle(adVo.getTitle());
            ad.setDescription(adVo.getDescription());

            baseDao.update(ad);

        }catch (Exception e){
            throw new DataBaseException("update ad failed");
        }
        return new ResultVo().success(adVo);
    }


    @Override
    public ResultVo deleteAdById(Integer adId) throws DataBaseException {
        try {
            Ad ad=adDao.getById(adId);
            baseDao.delete(ad);
            DataUtil.deleteAllPictures(ad);
        }catch (Exception e){
            throw new DataBaseException("delete ad failed");
        }
        return new ResultVo().success();
    }


    @Override
    public ResultVo searchAdsByKey(String Key, Integer pageNumber, Integer size) throws DataBaseException {
        List<AdVo> adVoList = new ArrayList<>();
        PageVo<AdVo> pageVo = new PageVo<>();

        Page<Ad> ads;
        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "adId");
            Pageable pageable = PageRequest.of(pageNumber, size, sort);
            ads = adRepository.searchAds(Key, pageable);
            setPageVo(adVoList, pageVo, ads);
        } catch (Exception e) {
            throw new DataBaseException("get ads failed");
        }
        return new ResultVo().success(pageVo);
    }
}

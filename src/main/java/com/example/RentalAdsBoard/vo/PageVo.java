package com.example.RentalAdsBoard.vo;

import com.example.RentalAdsBoard.entity.Ad;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
@Data
public class PageVo<T> {
    List<T> voList;
    long totalPages;
    public void setPageVo(List<AdVo> adVoList, PageVo<AdVo> pageVo, Page<Ad> page) {
        long totalPage=page.getTotalPages();

        for (Ad ad:page){
            AdVo adVo=new AdVo();
            adVo.setAdVo(ad);
            adVoList.add(adVo);
        }

        pageVo.setVoList(adVoList);
        pageVo.setTotalPages(totalPage);
    }
}

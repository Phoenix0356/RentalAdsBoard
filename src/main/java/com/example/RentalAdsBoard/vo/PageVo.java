package com.example.RentalAdsBoard.vo;

import com.example.RentalAdsBoard.entity.Ad;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
@Data
public class PageVo {
    List<AdVo> list;
    long totalPage;
}

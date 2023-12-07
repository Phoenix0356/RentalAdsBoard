package com.example.RentalAdsBoard.vo;

import lombok.Data;

import java.util.List;
@Data
public class PageVo<T> {
    List<T> voList;
    long totalPages;

}

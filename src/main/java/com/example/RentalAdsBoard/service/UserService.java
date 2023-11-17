package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.vo.ResultBean;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResultBean getUserById(Integer userId);
}

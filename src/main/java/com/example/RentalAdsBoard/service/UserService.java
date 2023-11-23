package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.vo.LoginVo;
import com.example.RentalAdsBoard.vo.RegisterVo;
import com.example.RentalAdsBoard.vo.ResultBean;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResultBean getUserById(Integer userId);

//    ResultBean getByUsername(String username);

    ResultBean register(RegisterVo registerVo);

    ResultBean login(LoginVo loginVo);
}

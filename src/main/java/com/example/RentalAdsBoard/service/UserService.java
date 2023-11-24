package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.vo.LoginVo;
import com.example.RentalAdsBoard.vo.RegisterVo;
import com.example.RentalAdsBoard.vo.ResultBean;
import com.example.RentalAdsBoard.vo.UserVo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResultBean getUserById(Integer userId);

//    ResultBean getByUsername(String username);

    ResultBean updateUserById(UserVo userVo);

    ResultBean updateUserPassword(UserVo userVo);

    ResultBean deleteUserById(Integer userId);

    ResultBean register(RegisterVo registerVo);

    ResultBean login(LoginVo loginVo);
}

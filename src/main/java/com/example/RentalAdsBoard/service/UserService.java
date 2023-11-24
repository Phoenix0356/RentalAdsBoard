package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.vo.LoginVo;
import com.example.RentalAdsBoard.vo.RegisterVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import com.example.RentalAdsBoard.vo.UserVo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResultVo getUserById(Integer userId);

//    ResultVo getByUsername(String username);

    ResultVo updateUserById(UserVo userVo);

    ResultVo updateUserPassword(UserVo userVo);

    ResultVo deleteUserById(Integer userId);

    ResultVo register(RegisterVo registerVo);

    ResultVo login(LoginVo loginVo);
}

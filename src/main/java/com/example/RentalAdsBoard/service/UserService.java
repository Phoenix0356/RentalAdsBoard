package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.vo.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResultVo getUserById(Integer userId);

//    ResultVo getByUsername(String username);

    ResultVo updateUserById(Integer userId,UserVo userVo);

    ResultVo updateUserPassword(Integer userId,UserVo userVo);

    ResultVo deleteUserById(Integer userId);

    ResultVo getUsersList();

    ResultVo register(RegisterVo registerVo);

    ResultVo login(LoginVo loginVo);

    ResultVo manageAuthority(AuthorityVo authorityVo);
}

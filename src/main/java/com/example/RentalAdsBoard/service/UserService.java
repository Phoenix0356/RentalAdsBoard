package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.vo.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResultVo updateUserById(Integer userId, UserVo userVo);

    ResultVo updateUserPassword(Integer userId,UserVo userVo);

    ResultVo resetPasswordByManager(String username);

    ResultVo deleteUserById(Integer userId);

    ResultVo getUser(Integer userId, String username) throws Exception;

    ResultVo getUsersList(Integer pageNumber,Integer size);

    ResultVo deleteUserByAdmin(String username);

    ResultVo register(RegisterVo registerVo);

    ResultVo login(LoginVo loginVo);

    ResultVo manageAuthority(String username, Integer level,Integer userId);
}

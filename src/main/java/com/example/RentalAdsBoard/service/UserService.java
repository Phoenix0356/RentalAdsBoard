package com.example.RentalAdsBoard.service;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.vo.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResultVo updateUserById(Integer userId, UserVo userVo) throws DataBaseException;

    ResultVo updateUserPassword(Integer userId,UserVo userVo) throws DataBaseException;

    ResultVo resetPasswordByManager(String username) throws DataBaseException;

    ResultVo deleteUserById(Integer userId) throws DataBaseException;

    ResultVo getUser(Integer userId, String username) throws DataBaseException;

    ResultVo getUsersList(Integer pageNumber,Integer size) throws DataBaseException;

    ResultVo deleteUserByAdmin(String username) throws DataBaseException;

    ResultVo register(RegisterVo registerVo) throws DataBaseException;

    ResultVo login(LoginVo loginVo) throws DataBaseException;

    ResultVo manageAuthority(String username, Integer level,Integer userId) throws DataBaseException;
}

package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.UserService;

import com.example.RentalAdsBoard.util.PasswordEncoder;
import com.example.RentalAdsBoard.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService  {
    @Autowired
    UserDao userDao;
    @Autowired
    BaseDao<User> baseDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public ResultVo getUserById(Integer userId){
        User user;
        try {
            user=userDao.getById(userId);
            user.setPassword(null);
        } catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(user);
    }
    @Override
    public ResultVo updateUserById(UserVo userVo){
        User user;
        try {
            user=userDao.getById(userVo.getUserId());
            user.setEmail(userVo.getEmail());
            baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(user);
    }
    @Override
    public ResultVo updateUserPassword(UserVo userVo){
        User user;
        try {
            user=userDao.getById(userVo.getUserId());
            String newPassword= user.getPassword();
            if (passwordEncoder.matchPassword(newPassword,user.getPassword())) user.setPassword(newPassword);
            else return new ResultVo().error();
            baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(user);
    }
    @Override
    public ResultVo deleteUserById(Integer userId){
        try {
            baseDao.delete(userDao.getById(userId));
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success();
    }

    @Override
    public ResultVo getUsersList(){
        List<User> list;
        try {
            list=userDao.getUsersList();
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(list);
    }
    @Override
    public ResultVo register(RegisterVo registerVo){
        User user=new User();
        user.setUsername(registerVo.getUsername());
        user.setPassword(passwordEncoder.encodePassword(registerVo.getPassword()));
        user.setEmail(registerVo.getEmail());
        user.setRole(registerVo.getRole());
        try {
            baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(user);
    }

    @Override
    public ResultVo login(LoginVo loginVo){
        User user;
        try {
            user=userDao.getByUsername(loginVo.getUsername());
        } catch (Exception e){
            return new ResultVo().error();
        }
        return passwordEncoder.matchPassword(loginVo.getPassword(),user.getPassword())?
                new ResultVo().success(user):new ResultVo().error();
    }

    @Override
    public ResultVo manageAuthority(AuthorityVo authorityVo) {
        try {
            User user=userDao.getByUsername(authorityVo.getUsername());
            user.setRole(authorityVo.getLevel());
            baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success();
    }


}

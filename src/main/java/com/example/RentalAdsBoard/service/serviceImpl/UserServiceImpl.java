package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.UserService;

import com.example.RentalAdsBoard.util.PasswordEncoder;
import com.example.RentalAdsBoard.vo.LoginVo;
import com.example.RentalAdsBoard.vo.RegisterVo;
import com.example.RentalAdsBoard.vo.ResultBean;
import com.example.RentalAdsBoard.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService  {
    @Autowired
    UserDao userDao;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public ResultBean getUserById(Integer userId){
        User user;
        try {
            user=userDao.getById(userId);
            user.setPassword(null);
        } catch (Exception e){
            return new ResultBean().error();
        }
        return new ResultBean().success(user);
    }
    @Override
    public ResultBean updateUserById(UserVo userVo){
        User user;
        try {
            user=userDao.getById(userVo.getUserId());
            user.setEmail(userVo.getEmail());
            userDao.save(user);
        }catch (Exception e){
            return new ResultBean().error();
        }
        return new ResultBean().success(user);
    }
    @Override
    public ResultBean updateUserPassword(UserVo userVo){
        User user;
        try {
            user=userDao.getById(userVo.getUserId());
            String newPassword= user.getPassword();
            if (passwordEncoder.matchPassword(newPassword,user.getPassword())) user.setPassword(newPassword);
            else return new ResultBean().error();
            userDao.save(user);
        }catch (Exception e){
            return new ResultBean().error();
        }
        return new ResultBean().success(user);
    }
    @Override
    public ResultBean deleteUserById(Integer userId){
        try {
            userDao.delete(userId);
        }catch (Exception e){
            return new ResultBean().error();
        }
        return new ResultBean().success();
    }

//    @Override
//    public ResultBean getByUsername(String username){
//        User user;
//        try {
//            user=userDao.getByUsername(username);
//        }catch (Exception e){
//            return new ResultBean().error();
//        }
//        return new ResultBean().success(user);
//    }
    @Override
    public ResultBean register(RegisterVo registerVo){
        User user=new User();
        user.setUsername(registerVo.getUsername());
        user.setPassword(passwordEncoder.encodePassword(registerVo.getPassword()));
        user.setEmail(registerVo.getEmail());
        user.setRole(registerVo.getRole());
        try {
            userDao.save(user);
        }catch (Exception e){
            return new ResultBean().error();
        }
        return new ResultBean().success(user);
    }

    @Override
    public ResultBean login(LoginVo loginVo){
        User user;
        try {
            user=userDao.getByUsername(loginVo.getUsername());
        } catch (Exception e){
            return new ResultBean().error();
        }
        return passwordEncoder.matchPassword(loginVo.getPassword(),user.getPassword())?
                new ResultBean().success(user):new ResultBean().error();
    }
}

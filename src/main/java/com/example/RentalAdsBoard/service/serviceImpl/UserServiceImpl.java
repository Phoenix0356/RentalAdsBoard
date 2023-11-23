package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.UserService;
import com.example.RentalAdsBoard.util.Security;
import com.example.RentalAdsBoard.vo.LoginVo;
import com.example.RentalAdsBoard.vo.RegisterVo;
import com.example.RentalAdsBoard.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService  {
    @Autowired
    UserDao userDao;
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
        user.setPassword(Security.encodePassword(registerVo.getPassword()));
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
        return Security.matchPassword(loginVo.getPassword(),user.getPassword())?
                new ResultBean().success(user):new ResultBean().error();
    }
}

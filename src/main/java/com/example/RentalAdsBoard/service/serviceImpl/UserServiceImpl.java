package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.UserService;
import com.example.RentalAdsBoard.vo.ResultBean;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService  {
    UserDao userDao=new UserDao();
    @Override
    public ResultBean getUserById(Integer userId){
        User user=userDao.getById(userId);
        if (user==null) return new ResultBean().error();
        return new ResultBean().success(user);
    }
}

package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.UserService;

import com.example.RentalAdsBoard.util.DataUtil;
import com.example.RentalAdsBoard.util.JwtTokenUtil;
import com.example.RentalAdsBoard.util.PasswordEncoder;
import com.example.RentalAdsBoard.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService  {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BaseDao<User> baseDao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${avatar_storage.path}")
    private String path;
    @Override
    public ResultVo getUserById(Integer userId){
        UserVo userVo=new UserVo();
        try {
            User user=userDao.getById(userId);
            userVo.setUserVo(user);
        } catch (Exception e){
            return new ResultVo().error("get user info failed");
        }
        return new ResultVo().success(userVo);
    }

    @Override
    public ResultVo getUsersList(){
        List<UserVo> userVoList=new ArrayList<>();
        try {
            List<User> list=userDao.getUsersList();
            for (User user:list){
                UserVo userVo=new UserVo();
                userVo.setUserVo(user);
                userVoList.add(userVo);
            }

        }catch (Exception e){
            return new ResultVo().error("get user list failed");
        }
        return new ResultVo().success(userVoList);
    }
    @Override
    public ResultVo updateUserById(Integer userId,UserVo userVo){
        try {
            User user=userDao.getById(userId);
            user.setUsername(userVo.getUsername());
            user.setEmail(userVo.getEmail());
            user.setAvatarPath(DataUtil.saveOrUpdateImage(userVo.getAvatarBase64(), user.getAvatarPath(),path,true));
            baseDao.update(user);
        }catch (Exception e){
            return new ResultVo().error("update user info failed");
        }
        return new ResultVo().success(userVo);
    }
    @Override
    public ResultVo updateUserPassword(Integer userId,UserVo userVo){
        try {
            User user=userDao.getById(userId);
            String newPassword= user.getPassword();
            if (passwordEncoder.matchPassword(newPassword,user.getPassword())) user.setPassword(newPassword);
            else return new ResultVo().error("wrong password");
            baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error("reset password failed");
        }
        return new ResultVo().success();
    }
    @Override
    public ResultVo deleteUserById(Integer userId){
        try {
            baseDao.delete(userDao.getById(userId));
        }catch (Exception e){
            return new ResultVo().error("delete user failed");
        }
        return new ResultVo().success();
    }


    @Override
    public ResultVo register(RegisterVo registerVo){
        Integer userId;
        try {
            if (userDao.getByUsername(registerVo.getUsername())!=null){
                return new ResultVo().error("the name has already been taken");
            }
            User user=new User();
            user.setUsername(registerVo.getUsername());
            user.setPassword(passwordEncoder.encodePassword(registerVo.getPassword()));
            user.setEmail(registerVo.getEmail());
            user.setRole(Integer.parseInt(registerVo.getRole()));
            user.setAvatarPath(DataUtil.saveOrUpdateImage(registerVo.getAvatarBase64(),user.getAvatarPath(),path,true));
            userId=baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error("register failed");
        }
        return new ResultVo().success(jwtTokenUtil.createToken(userId,1));
    }

    @Override
    public ResultVo login(LoginVo loginVo){
        Integer userId,role;
        String password;
        try {
            User user=userDao.getByUsername(loginVo.getUsername());
            if (user==null) return new ResultVo().error("the username is invalidate");
            password=user.getPassword();
            userId=user.getUserId();
            role=user.getRole();


        } catch (Exception e){
            return new ResultVo().error("login failed");
        }
        return passwordEncoder.matchPassword(loginVo.getPassword(),password)?
                new ResultVo().success(jwtTokenUtil.createToken(userId,role)):new ResultVo().error("the password is wrong");
    }
    @Override
    public ResultVo manageAuthority(Integer userId, Integer level) {

        try {
            User user=userDao.getById(userId);
            user.setRole(level);
            baseDao.update(user);
        }catch (Exception e){
            return new ResultVo().error("manage level failed");
        }
        return new ResultVo().success();
    }


}

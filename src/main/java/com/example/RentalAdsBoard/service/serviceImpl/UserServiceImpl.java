package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.AdDao;
import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.UserService;

import com.example.RentalAdsBoard.util.DataUtil;
import com.example.RentalAdsBoard.util.PasswordEncoder;
import com.example.RentalAdsBoard.vo.*;
import net.bytebuddy.utility.nullability.AlwaysNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService  {
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
            return new ResultVo().error();
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
            return new ResultVo().error();
        }
        return new ResultVo().success(userVoList);
    }
    @Override
    public ResultVo updateUserById(UserVo userVo){
        try {
            User user=userDao.getById(userVo.getUserId());
            user.setEmail(userVo.getEmail());
            user.setAvatarPath(DataUtil.saveOrUpdateImage(userVo.getAvatarBase64(), user.getAvatarPath(),path,true));
            baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(userVo);
    }
    @Override
    public ResultVo updateUserPassword(UserVo userVo){
        try {
            User user=userDao.getById(userVo.getUserId());
            String newPassword= user.getPassword();
            if (passwordEncoder.matchPassword(newPassword,user.getPassword())) user.setPassword(newPassword);
            else return new ResultVo().error();
            baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success();
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
    public ResultVo register(RegisterVo registerVo){

        try {
            if (userDao.getByUsername(registerVo.getUsername())!=null){
                return new ResultVo().error();
            }
            User user=new User();
            user.setUsername(registerVo.getUsername());
            user.setPassword(passwordEncoder.encodePassword(registerVo.getPassword()));
            user.setEmail(registerVo.getEmail());
            user.setRole(Integer.parseInt(registerVo.getRole()));
            user.setAvatarPath(DataUtil.saveOrUpdateImage(registerVo.getAvatarBase64(),user.getAvatarPath(),path,true));
            baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success();
    }

    @Override
    public ResultVo login(LoginVo loginVo){
        UserVo userVo=new UserVo();
        String password;
        try {
            User user=userDao.getByUsername(loginVo.getUsername());
            if (user==null) return new ResultVo().error();
            password=user.getPassword();

            userVo.setUserVo(user);
        } catch (Exception e){
            return new ResultVo().error();
        }
        return passwordEncoder.matchPassword(loginVo.getPassword(),password)?
                new ResultVo().success(userVo):new ResultVo().error();
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

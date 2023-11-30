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

import java.util.List;

@Service
public class UserServiceImpl implements UserService  {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BaseDao<User> baseDao;
    @Autowired
    private DataUtil dataUtil;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${avatar_storage.path}")
    private String path;
    @Override
    public ResultVo getUserById(Integer userId){
        UserVo userVo=new UserVo();
        try {
            User user=userDao.getById(userId);
            userVo.setUsername(user.getUsername());
            userVo.setEmail(user.getEmail());
//            userVo.setAvatarBase64(dataUtil.pictureToBase64(user.getAvatarPath()));
        } catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(userVo);
    }
    @Override
    public ResultVo updateUserById(UserVo userVo){
        User user;
        try {
            user=userDao.getById(userVo.getUserId());
            user.setEmail(userVo.getEmail());
            user.setAvatarPath(dataUtil.saveOrUpdateImage(userVo.getAvatarBase64(), user.getAvatarPath(),path,true));
//            user.setAvatarPath(user.getAvatarPath());
            baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(user);
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
        user.setRole(Integer.parseInt(registerVo.getRole()));
        user.setAvatarPath(dataUtil.saveOrUpdateImage(registerVo.getAvatarBase64(),user.getAvatarPath(),path,true));
//        user.setAvatarPath(registerVo.getAvatarBase64());
        try {
            if (userDao.getByUsername(registerVo.getUsername())!=null){
                return new ResultVo().error();
            }
            baseDao.save(user);
        }catch (Exception e){
            return new ResultVo().error();
        }
        return new ResultVo().success(user);
    }

    @Override
    public ResultVo login(LoginVo loginVo){
        User user;
        String password;
        try {
            user=userDao.getByUsername(loginVo.getUsername());
            if (user==null) return new ResultVo().error();
            password=user.getPassword();
            user.setPassword(null);
        } catch (Exception e){
            return new ResultVo().error();
        }
        return passwordEncoder.matchPassword(loginVo.getPassword(),password)?
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

package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.dao.pageDao.UserPageDao;
import com.example.RentalAdsBoard.entity.Ad;
import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.service.UserService;

import com.example.RentalAdsBoard.util.DataUtil;
import com.example.RentalAdsBoard.util.JwtTokenUtil;
import com.example.RentalAdsBoard.util.PasswordEncoder;
import com.example.RentalAdsBoard.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService  {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private UserDao userDao;
    @Autowired
    private BaseDao<User> baseDao;
    @Autowired
    private UserPageDao userPageDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResultVo getUser(Integer userId, String username) throws DataBaseException {
        UserVo userVo=new UserVo();
        try {
            User user=username==null?userDao.getById(userId):userDao.getByUsername(username);
            userVo.setUserVo(user);
        } catch (Exception e){

            throw new DataBaseException("Failed loading user info");
        }
        return new ResultVo().success(userVo);
    }

    @Override
    public ResultVo getUsersList(Integer pageNumber, Integer size) throws DataBaseException {
        List<UserVo> userVoList=new ArrayList<>();
        PageVo<UserVo> pageVo=new PageVo<>();

        try {
            Sort sort = Sort.by(Sort.Direction.DESC, "userId");
            Pageable pageable= PageRequest.of(pageNumber, size,sort);
            Page<User> page=userPageDao.findAll(pageable);
            long totalPages=page.getTotalPages();

            for (User user:page){
                UserVo userVo=new UserVo();
                userVo.setUserVo(user);
                userVoList.add(userVo);
            }

            pageVo.setVoList(userVoList);
            pageVo.setTotalPages(totalPages);

        }catch (Exception e){
            throw new DataBaseException("Failed loading users list");
        }
        return new ResultVo().success(pageVo);
    }
    @Override
    public ResultVo updateUserById(Integer userId,UserVo userVo) throws DataBaseException {
        try {
            User user=userDao.getById(userId);
            user.setUsername(userVo.getUsername());
            user.setEmail(userVo.getEmail());
            user.setAvatarPath(DataUtil.saveOrUpdateImage(userVo.getAvatarBase64(), user.getAvatarPath(),"static\\avatar",true));
            baseDao.update(user);
        }catch (Exception e){
            throw new DataBaseException("Failed updating user info");
        }
        return new ResultVo().success(userVo);
    }
    @Override
    public ResultVo updateUserPassword(Integer userId,UserVo userVo) throws DataBaseException {
        try {
            User user=userDao.getById(userId);
            String originPassword= userVo.getOriginPassword();

            String newPassword= passwordEncoder.encodePassword(userVo.getNewPassword());

            if (newPassword.equals(user.getPassword())){
                throw new DataBaseException("The new password can't be the same with the original password");
            }
            if (passwordEncoder.matchPassword(originPassword, user.getPassword())) {
                user.setPassword(newPassword);
            }
            else throw new DataBaseException("Wrong password");

            baseDao.update(user);
        }catch (Exception e){
            throw new DataBaseException("Failed resetting password");
        }
        return new ResultVo().success();
    }

    @Override
    public ResultVo resetPasswordByManager(String username) throws DataBaseException {
        try {
            User user=userDao.getByUsername(username);
            user.setPassword(passwordEncoder.encodePassword("12345"));
            baseDao.update(user);
        }catch (Exception e){
            throw new DataBaseException("Failed resetting password");
        }

        return new ResultVo().success();
    }
    @Override
    public ResultVo deleteUserById(Integer userId) throws DataBaseException {
        try {
            User user=userDao.getById(userId);
            baseDao.delete(user);
            DataUtil.deleteAllImages(user);
        }catch (Exception e){
            throw new DataBaseException("Failed deleting user");
        }
        return new ResultVo().success();
    }

    @Override
    public ResultVo deleteUserByAdmin(String username) throws DataBaseException {
        try {
            User user=userDao.getByUsername(username);
            baseDao.delete(user);
            DataUtil.deleteAllImages(user);
        }catch (Exception e){
            throw new DataBaseException("Failed deleting user");
        }
        return new ResultVo().success();
    }

    @Override
    public ResultVo register(RegisterVo registerVo) throws DataBaseException {
        Integer userId;
        try {
            if (userDao.getByUsername(registerVo.getUsername())!=null){
                throw new DataBaseException("The name has already been taken");
            }
            User user=new User();
            user.setUsername(registerVo.getUsername());
            user.setPassword(passwordEncoder.encodePassword(registerVo.getPassword()));
            user.setEmail(registerVo.getEmail());
            user.setRole(Integer.parseInt(registerVo.getRole()));
            user.setAvatarPath(DataUtil.saveOrUpdateImage(registerVo.getAvatarBase64(),user.getAvatarPath(),"static\\avatar",true));
            userId=baseDao.save(user);
        }catch (Exception e){
            throw new DataBaseException("Register failed");
        }
        return new ResultVo().success(jwtTokenUtil.genToken(userId,1));
    }

    @Override
    public ResultVo login(LoginVo loginVo) throws DataBaseException {
        Integer userId,role;
        String password;
        try {
            User user=userDao.getByUsername(loginVo.getUsername());
            if (user==null) throw new DataBaseException("Username not found, please register first");
            password=user.getPassword();
            userId=user.getUserId();
            role=user.getRole();

        } catch (Exception e){
            throw new DataBaseException("Login failed");
        }
        return passwordEncoder.matchPassword(loginVo.getPassword(),password)?
                new ResultVo().success(jwtTokenUtil.genToken(userId,role)):new ResultVo().error("Wrong password");
    }
    @Override
    public ResultVo manageAuthority(String username, Integer level, Integer userId) throws DataBaseException {

        try {
            User userTarget=userDao.getByUsername(username);
            userTarget.setRole(level);
            baseDao.update(userTarget);
            if (Objects.equals(userTarget.getUserId(), userId)) {
                return new ResultVo().success(jwtTokenUtil.genToken(userId,level));
            }
        }catch (Exception e){
            throw new DataBaseException("Failed changing role");
        }
        return new ResultVo().success();
    }
}

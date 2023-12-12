package com.example.RentalAdsBoard.service.serviceImpl;

import com.example.RentalAdsBoard.dao.BaseDao;
import com.example.RentalAdsBoard.dao.UserDao;
import com.example.RentalAdsBoard.dao.pageDao.UserPageDao;
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
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserPageDao userPageDao;
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
    public ResultVo getUsersList(Integer pageNumber, Integer size){
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
            return new ResultVo().error("get user list failed");
        }
        return new ResultVo().success(pageVo);
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
            String originPassword= userVo.getOriginPassword();

            String newPassword= passwordEncoder.encodePassword(userVo.getNewPassword());

            if (newPassword.equals(user.getPassword())){
                return new ResultVo().error("The new password can't be the same with the original password");
            }
            if (passwordEncoder.matchPassword(originPassword, user.getPassword())) {
                user.setPassword(newPassword);
            }
            else return new ResultVo().error("wrong password");

            baseDao.update(user);
        }catch (Exception e){
            return new ResultVo().error("reset password failed");
        }
        return new ResultVo().success();
    }

    @Override
    public ResultVo resetPasswordByManager(String username){
        try {
            User user=userDao.getByUsername(username);
            user.setPassword(passwordEncoder.encodePassword("12345"));
            baseDao.update(user);
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
    public ResultVo deleteUserByAdmin(String username){
        try {
            baseDao.delete(userDao.getByUsername(username));
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
    public ResultVo manageAuthority(String username, Integer level, Integer userId) {

        try {
            User userTarget=userDao.getByUsername(username);
            userTarget.setRole(level);
            baseDao.update(userTarget);
            if (Objects.equals(userTarget.getUserId(), userId)) {
                return new ResultVo().success(jwtTokenUtil.createToken(userId,level));
            }
        }catch (Exception e){
            return new ResultVo().error("manage level failed");
        }
        return new ResultVo().success();
    }


}

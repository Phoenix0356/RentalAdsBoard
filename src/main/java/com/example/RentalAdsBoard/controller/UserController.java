package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.service.UserService;
import com.example.RentalAdsBoard.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/user/get")
    public ResultVo getUser(HttpServletRequest request,
                            @RequestParam("username") Optional<String> username) throws Exception {
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.getUser(userId,username.orElse(null));
    }

    @PutMapping("/user/update/info")
    public ResultVo updateUserById(HttpServletRequest request,
                                   @RequestBody()UserVo userVo){
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.updateUserById((Integer) userId,userVo);
    }
    @PostMapping("/user/login")
    public ResultVo login(@RequestBody() LoginVo loginVo){

        return userService.login(loginVo);
    }
    @PostMapping("/user/register")
    public ResultVo register(@RequestBody()RegisterVo registerVo){
        return userService.register(registerVo);
    }
    @PutMapping("/user/update/password")
    public ResultVo updatePassword(HttpServletRequest request,
                                   @RequestBody() UserVo userVo){
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.updateUserPassword(userId,userVo);
    }
    @DeleteMapping("/user/delete")
    public ResultVo deleteUserById(HttpServletRequest request) {
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.deleteUserById(userId);
    }
    @GetMapping("/user/list")
    public ResultVo getUsersList(){
        return userService.getUsersList();
    }

    @DeleteMapping("/user/admin/delete")
    public ResultVo deleteUserByAdmin(HttpServletRequest request,
                                      @RequestParam("username") String username){
        Integer role=(Integer) request.getAttribute("role");
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.deleteUserByAdmin(username);
    }

    @PutMapping("/user/admin/role")
    public ResultVo manageAuthority(HttpServletRequest request,
                                    @RequestParam("roleChanged") Integer roleChanged,
                                    @RequestParam("username") String username){
        Integer userId=(Integer) request.getAttribute("userId");
        Integer role=(Integer) request.getAttribute("role");
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.manageAuthority(username,roleChanged,userId);
    }
    @PutMapping("/user/admin/resetPassword")
    public ResultVo resetPassword(HttpServletRequest request,
                                   @RequestParam("username") String username){
        Integer role=(Integer) request.getAttribute("role");
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.resetPasswordByManager(username);
    }
}

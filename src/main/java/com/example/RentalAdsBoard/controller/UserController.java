package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.service.UserService;
import com.example.RentalAdsBoard.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class UserController extends BaseController{
    @Autowired
    UserService userService;

    @GetMapping("/board/home")
    public ResultVo getUserById(@ModelAttribute("userId") Integer userId){
        return userService.getUserById(userId);
    }
    @GetMapping("/board/root")
    public ResultVo getUsersList(){
        return userService.getUsersList();
    }
    @PutMapping("/board/update")
    public ResultVo updateUserById(@ModelAttribute("userId") Integer userId,
                                   @RequestBody()UserVo userVo){
        userVo.setUserId(userId);
        return userService.updateUserById(userVo);
    }
    @PutMapping("/board/root/manage")
    public ResultVo manageAuthority(@RequestBody()AuthorityVo authorityVo){
        return userService.manageAuthority(authorityVo);
    }
    @PostMapping("/board/login")
    public ResultVo login(@RequestBody() LoginVo loginVo){
        return userService.login(loginVo);
    }
    @PostMapping("/board/register")
    public ResultVo register(@RequestBody()RegisterVo registerVo){
        return userService.register(registerVo);
    }
    @PutMapping("/board/update/password")
    public ResultVo updatePassword(@ModelAttribute("userId") Integer userId,
                                   @RequestBody() UserVo userVo){
        userVo.setUserId(userId);
        return userService.updateUserPassword(userVo);
    }
    @DeleteMapping("/board/delete")
    public ResultVo deleteUserById(@ModelAttribute("userId") Integer userId){
        return userService.deleteUserById(userId);
    }
}

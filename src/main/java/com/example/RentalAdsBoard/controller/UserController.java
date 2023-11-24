package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.service.UserService;
import com.example.RentalAdsBoard.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/board/home")
    public ResultVo getUserById(@RequestParam("user_id") Integer userId){
        return userService.getUserById(userId);
    }
    @GetMapping("/board/root")
    public ResultVo getUsersList(){
        return userService.getUsersList();
    }
    @PutMapping("/board/update")
    public ResultVo updateUserById(@RequestBody()UserVo userVo){
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
    public ResultVo register(@RequestParam()RegisterVo registerVo){
        return userService.register(registerVo);
    }
    @PutMapping("/board/update/password")
    public ResultVo updatePassword(@RequestBody() UserVo userVo){
        return userService.updateUserPassword(userVo);
    }
    @DeleteMapping("/board/delete")
    public ResultVo deleteUserById(@RequestParam("user_id") Integer userId){
        return userService.deleteUserById(userId);
    }


}

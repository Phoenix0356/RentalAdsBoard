package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.service.UserService;
import com.example.RentalAdsBoard.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
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

        return userService.updateUserById(userId,userVo);
    }
    @PutMapping("/board/root/manage")
    public ResultVo manageAuthority(@ModelAttribute("userId") Integer userId,
                                    @RequestParam("level")Integer level){
        return userService.manageAuthority(userId,level);
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

        return userService.updateUserPassword(userId,userVo);
    }
    @DeleteMapping("/board/delete")
    public ResultVo deleteUserById(@ModelAttribute("userId") Integer userId){
        return userService.deleteUserById(userId);
    }
}

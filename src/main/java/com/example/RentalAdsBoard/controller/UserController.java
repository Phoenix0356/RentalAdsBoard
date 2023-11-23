package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.service.UserService;
import com.example.RentalAdsBoard.vo.LoginVo;
import com.example.RentalAdsBoard.vo.RegisterVo;
import com.example.RentalAdsBoard.vo.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/board/home")
    public ResultBean getUserById(@RequestParam("user_id") Integer userId){
        return userService.getUserById(userId);
    }
    @PostMapping("/board/login")
    public ResultBean login(@RequestBody() LoginVo loginVo){
        return userService.login(loginVo);
    }

    @PostMapping("/board/register")
    public ResultBean register(@RequestParam()RegisterVo registerVo){
        return userService.register(registerVo);
    }


}

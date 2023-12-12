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
    public ResultVo getUserById(@ModelAttribute("userId") Integer userId,
                                @RequestParam(value = "username",required = false) String username){

        return userService.getUser(userId,username);
    }

    @PutMapping("/board/update")
    public ResultVo updateUserById(@ModelAttribute("userId") Integer userId,
                                   @RequestBody()UserVo userVo){

        return userService.updateUserById(userId,userVo);
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
    @GetMapping("/board/root")
    public ResultVo getUsersList(@RequestParam("page_number") Integer pageNumber,
                                 @RequestParam("size")Integer size){
        return userService.getUsersList(pageNumber,size);
    }

    @DeleteMapping("/board/root/delete")
    public ResultVo deleteUserByAdmin(@ModelAttribute("role") Integer role,
                                      @RequestParam("username") String username){

        if (role<2) return new ResultVo().error("Permission denied");
        return userService.deleteUserByAdmin(username);
    }

    @PutMapping("/board/root/manage")
    public ResultVo manageAuthority(@ModelAttribute("role") Integer role,
                                    @ModelAttribute("userId") Integer userId,
                                    @RequestParam("roleChanged") Integer roleChanged,
                                    @RequestParam("username") String username){
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.manageAuthority(username,roleChanged,userId);
    }
    @PutMapping("/board/root/resetPassword")
    public ResultVo resetPassword(@ModelAttribute("role") Integer role,
                                   @RequestParam("username") String username){
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.resetPasswordByManager(username);
    }
}

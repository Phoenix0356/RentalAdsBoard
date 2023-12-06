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
    @GetMapping("/board/home")
    public ResultVo getUser(HttpServletRequest request,
                            @RequestParam("username") Optional<String> username) throws Exception {
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.getUser(userId,username.orElse(null));
    }

    @PutMapping("/board/update")
    public ResultVo updateUserById(HttpServletRequest request,
                                   @RequestBody()UserVo userVo){
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.updateUserById((Integer) userId,userVo);
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
    public ResultVo updatePassword(HttpServletRequest request,
                                   @RequestBody() UserVo userVo){
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.updateUserPassword(userId,userVo);
    }
    @DeleteMapping("/board/delete")
    public ResultVo deleteUserById(HttpServletRequest request) {
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.deleteUserById(userId);
    }
    @GetMapping("/board/root")
    public ResultVo getUsersList(){
        return userService.getUsersList();
    }

    @DeleteMapping("/board/root/delete")
    public ResultVo deleteUserByAdmin(HttpServletRequest request,
                                      @RequestParam("username") String username){
        Integer role=(Integer) request.getAttribute("role");
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.deleteUserByAdmin(username);
    }

    @PutMapping("/board/root/manage")
    public ResultVo manageAuthority(HttpServletRequest request,
                                    @RequestParam("roleChanged") Integer roleChanged,
                                    @RequestParam("username") String username){
        Integer userId=(Integer) request.getAttribute("userId");
        Integer role=(Integer) request.getAttribute("role");
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.manageAuthority(username,roleChanged,userId);
    }
    @PutMapping("/board/root/resetPassword")
    public ResultVo resetPassword(HttpServletRequest request,
                                   @RequestParam("username") String username){
        Integer role=(Integer) request.getAttribute("role");
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.resetPasswordByManager(username);
    }
}

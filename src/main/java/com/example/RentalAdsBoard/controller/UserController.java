package com.example.RentalAdsBoard.controller;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.service.UserService;
import com.example.RentalAdsBoard.vo.LoginVo;
import com.example.RentalAdsBoard.vo.RegisterVo;
import com.example.RentalAdsBoard.vo.ResultVo;
import com.example.RentalAdsBoard.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @GetMapping("/board/home")
    public ResultVo getUser(HttpServletRequest request,
                            @RequestParam("username") Optional<String> username) throws DataBaseException {
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.getUser(userId,username.orElse(null));
    }

    @PutMapping("/board/update")
    public ResultVo updateUserById(HttpServletRequest request,
                                   @RequestBody()UserVo userVo) throws DataBaseException {
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.updateUserById((Integer) userId,userVo);
    }
    @PostMapping("/board/login")
    public ResultVo login(@RequestBody() LoginVo loginVo) throws DataBaseException {

        return userService.login(loginVo);
    }
    @PostMapping("/board/register")
    public ResultVo register(@RequestBody()RegisterVo registerVo) throws DataBaseException {
        return userService.register(registerVo);
    }
    @PutMapping("/board/update/password")
    public ResultVo updatePassword(HttpServletRequest request,
                                   @RequestBody() UserVo userVo) throws DataBaseException {
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.updateUserPassword(userId,userVo);
    }
    @DeleteMapping("/board/delete")
    public ResultVo deleteUserById(HttpServletRequest request) throws DataBaseException {
        Integer userId=(Integer) request.getAttribute("userId");
        return userService.deleteUserById(userId);
    }
    @GetMapping("/board/root")
    public ResultVo getUsersList(@RequestParam("page_number") Integer pageNumber,
                                 @RequestParam("size")Integer size) throws DataBaseException {
        return userService.getUsersList(pageNumber,size);
    }

    @DeleteMapping("/board/root/delete")
    public ResultVo deleteUserByAdmin(HttpServletRequest request,
                                      @RequestParam("username") String username) throws DataBaseException {
        Integer role=(Integer) request.getAttribute("role");
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.deleteUserByAdmin(username);
    }

    @PutMapping("/board/root/manage")
    public ResultVo manageAuthority(HttpServletRequest request,
                                    @RequestParam("roleChanged") Integer roleChanged,
                                    @RequestParam("username") String username) throws DataBaseException {
        Integer userId=(Integer) request.getAttribute("userId");
        Integer role=(Integer) request.getAttribute("role");
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.manageAuthority(username,roleChanged,userId);
    }
    @PutMapping("/board/root/resetPassword")
    public ResultVo resetPassword(HttpServletRequest request,
                                   @RequestParam("username") String username) throws DataBaseException {
        Integer role=(Integer) request.getAttribute("role");
        if (role<2) return new ResultVo().error("Permission denied");
        return userService.resetPasswordByManager(username);
    }
}

package com.example.RentalAdsBoard.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {
    @ModelAttribute("userId")
    public Integer getUserId(HttpServletRequest request) {
        return (Integer) request.getAttribute("userId");
    }
    @ModelAttribute("userId")
    public Integer getUserRole(HttpServletRequest request) {
        return (Integer) request.getAttribute("role");
    }

}

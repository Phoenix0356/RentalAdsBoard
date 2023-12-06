package com.example.RentalAdsBoard.controller.advice;

import com.example.RentalAdsBoard.vo.ResultVo;
import io.jsonwebtoken.JwtException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(JwtException.class)
    @ResponseBody
    public JwtException handleException(JwtException je) {
        return je;
    }
}

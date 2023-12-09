package com.example.RentalAdsBoard.controller.advice;

import com.example.RentalAdsBoard.controller.exception.DataBaseException;
import com.example.RentalAdsBoard.vo.ResultVo;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger("ExceptionLogger");
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultVo handleException(Exception e) {
        logger.error("system error",e);
        return new ResultVo().error("system error");
    }
    @ExceptionHandler(JwtException.class)
    @ResponseBody
    public ResultVo handleJwtException(JwtException je) {
        logger.error("token authorization error",je);
        return new ResultVo().error(je.getMessage());
    }

    @ExceptionHandler(DataBaseException.class)
    @ResponseBody
    public ResultVo handleDataBaseException(DataBaseException de) {
        logger.error("database operation error",de);
        return new ResultVo().error(de.getMessage());
    }
}

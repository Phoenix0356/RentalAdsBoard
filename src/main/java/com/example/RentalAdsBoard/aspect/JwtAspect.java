package com.example.RentalAdsBoard.aspect;
import io.jsonwebtoken.*;
import com.example.RentalAdsBoard.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class JwtAspect {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    @Pointcut(value = "execution(* com.example.RentalAdsBoard.controller.*Controller.*(..)) "
            + "&& execution(* com.example.RentalAdsBoard.websocket.ServerSocket.*(..)) "
            + "&& !execution(* com.example.RentalAdsBoard.controller.UserController.login(..)) "
            + "&& !execution(* com.example.RentalAdsBoard.controller.UserController.register(..))")
    public void point(){
    }
    @Before(value = "point()")
    public void authorize() throws JwtException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String header = request.getHeader("Authorization");
        if (header!=null&&header.startsWith("Bearer ")) {
            String token=header.substring(7);
            try {
                Claims claims = jwtTokenUtil.validateToken(token);
                Integer userId = Integer.parseInt(claims.get("sub").toString());
                Integer role = Integer.parseInt(claims.get("role").toString());
                request.setAttribute("userId", userId);
                request.setAttribute("role",role);
            } catch (JwtException je) {
                throw new JwtException("invalid token, need to login");
            }
        } else {
            throw new JwtException("illegal token");
        }
    }

}



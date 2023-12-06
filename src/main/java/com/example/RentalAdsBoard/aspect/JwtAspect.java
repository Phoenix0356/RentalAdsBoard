package com.example.RentalAdsBoard.aspect;

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
    @Pointcut(value = "execution(* com.example.RentalAdsBoard.controller.BaseController.*(..))")
    public void point(){
    }
    @Before(value = "point()")
    public boolean authorize(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 获取请求头中的token
        String header = request.getHeader("Authorization");

        // 验证token

        if (header!=null&&header.startsWith("Bearer ")) {
            String token=header.substring(7);
            Claims claims = jwtTokenUtil.validateToken(token);
            if (claims != null) {

                Integer userId = Integer.parseInt(claims.get("sub").toString());
                Integer role = Integer.parseInt(claims.get("role").toString());

                request.setAttribute("userId", userId);
                request.setAttribute("role",role);

            }else {
                return false;
            }
        }else {
            return false;
        }
        return true;
    }
}


package com.example.RentalAdsBoard.controller.interceptor;

import com.example.RentalAdsBoard.util.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    JwtTokenUtil jwtTokenUtil;
    ;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        final String header = request.getHeader("Authorization");
        if (header!=null&&header.startsWith("Bearer ")) {
            String token=header.substring(7);
            Claims claims = jwtTokenUtil.validateToken(token);
            if (claims != null) {
                // 解析token中的userid
                Integer userId = Integer.parseInt(claims.get("sub").toString());
                Integer role = Integer.parseInt(claims.get("role").toString());

                // 将userid添加到请求头中
                request.setAttribute("userId", userId);
                request.setAttribute("roleLevel",role);
                return true;
            }else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("illegal Token");
                return false;
            }
        }else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("need to login");
            return false;
        }
    }

}
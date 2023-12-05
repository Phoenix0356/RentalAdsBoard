package com.example.RentalAdsBoard.config;

import com.example.RentalAdsBoard.controller.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private final JwtInterceptor jwtInterceptor;

    @Value("${excludePaths}")
    String[] excludePaths;

    public WebConfig(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .excludePathPatterns(excludePaths);

    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOriginPatterns("*") // 使用allowedOriginPatterns
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
//                .allowedHeaders("*")
//                .allowCredentials(false);
//    }
}


package com.example.RentalAdsBoard.util;


import com.example.RentalAdsBoard.entity.User;
import com.example.RentalAdsBoard.vo.UserVo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtTokenUtil {

    @Value("${jwt.expiration}")
    private long expiration;
    @Value ("${jwt.secret}")
    private String secret;


    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
    }

    public String createToken(Integer userId,Integer role){
        HashMap<String,Object> claims=new HashMap<>();
        claims.put("sub",String.valueOf(userId));
        claims.put("role",String.valueOf(role));
        claims.put("exp",new Date(System.currentTimeMillis() + expiration*1000));
        return Jwts.builder()
                .claims(claims)
                .signWith(getSigningKey())
                .compact();
    }
    public Claims validateToken(String token) {
        Claims claims = null;
        claims = Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims;
    }

}

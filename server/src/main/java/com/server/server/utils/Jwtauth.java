package com.server.server.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

// making component for the reuse ment
@Component
public class Jwtauth {

    private String SECRET_KEY = "TaK+HaV^uvCHEFsEVfypW#7g9^k*Z8$V";

    private SecretKey signKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    public String getUserName(String token) {
        // Claims is basically a object that returns all token claims
        Claims claims = extrClaims(token);
        // getSubject it returns the name that are used in the claims that things return
        return claims.getSubject();
    }

    private Claims extrClaims(String token) {
        return Jwts.parser()
                .verifyWith(signKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generatetoken(String username) {
        Map<String, Object> claims = new HashMap<>(null);
        return createToken(claims, username);
    }

    public Date extractExpiration(String token) {
        return extrClaims(token).getExpiration();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .header().empty().add("typ", "JWT")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60)) // 5 minutes expiration time
                .signWith(signKey())
                .compact();
    }

    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}

package com.pasteleria.pasteleria.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {
    
    private final Key key;
    private final long expirationTimeInMillis;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.expiration}") long expirationTimeInMillis) {

        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.expirationTimeInMillis = expirationTimeInMillis;
    }

    public String generatetoken(String username){
        Date now =new Date();
        Date expiryDate = new Date(now.getTime() + expirationTimeInMillis);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token){
        return getClaims(token).getSubject();
    }

    public boolean isTokenValid(String token){
        try {
          Claims claims = getClaims(token);
          String username =claims.getSubject();
            return username != null && !username.isEmpty() && !claims.getExpiration().before(new Date());
    } catch (JwtException | IllegalArgumentException ex) {
        return false;
        }
    }

    private Claims getClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

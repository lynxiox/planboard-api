package com.example.planboardapi.auth.service;

import com.example.planboardapi.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Slf4j
@Service
public class JwtService {
    @Value("${spring.security.jwt.secret-key}")
    private String secretKey;
    @Value("${spring.security.jwt.expiration-time}")
    private long jwtExpiration;
    @Value("${spring.security.jwt.refresh-secret-key}")
    private String refreshSecretKey;
    @Value("${spring.security.jwt.refresh-expiration-time}")
    private long refreshJwtExpiration;


    public String extractUsername(String token, boolean isAccessToken) {
        return extractClaim(token, Claims::getSubject, isAccessToken);
    }

    public boolean isValid(String token, UserDetails user, boolean isAccessToken) {
        String username = extractUsername(token, isAccessToken);
        return (username.equals(user.getUsername())) && !isTokenExpired(token, isAccessToken);
    }

    private boolean isTokenExpired(String token, boolean isAccessToken) {
        return extractExpiration(token, isAccessToken).before(new Date());
    }

    private Date extractExpiration(String token, boolean isAccessToken) {
        return extractClaim(token, Claims::getExpiration, isAccessToken);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver, boolean isAccessToken) {
        Claims claims = extractAllClaims(token, isAccessToken);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token, boolean isAccessToken) {
        return Jwts.parser()
                .verifyWith(getSignKey(isAccessToken))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String generateToken(User user, boolean isAccessToken) {
        String token = Jwts
                .builder()
                .subject(user.getUsername())
                .claim("user_id", user.getId())
                .claim("role", user.getRole().name())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + getExpirationTime(isAccessToken)))
                .signWith(getSignKey(isAccessToken))
                .compact();

        log.info("Generated {} token for user: {}. Expiration date: {}",
                isAccessToken ? "access" : "refresh",
                user.getUsername(),
                new Date(System.currentTimeMillis() + getExpirationTime(isAccessToken)));

        return token;
    }

    private long getExpirationTime(boolean isAccessToken) {
        return isAccessToken ? jwtExpiration : refreshJwtExpiration;
    }

    private SecretKey getSignKey(boolean isAccessToken) {
        byte[] keyBytes = Decoders.BASE64URL.decode(isAccessToken ? secretKey : refreshSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractRole(String token, boolean isAccessToken) {
        return extractClaim(token, claims -> claims.get("role", String.class), isAccessToken);
    }
}

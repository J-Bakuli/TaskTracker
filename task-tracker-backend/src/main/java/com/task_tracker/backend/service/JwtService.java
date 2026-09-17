package com.task_tracker.backend.service;

import com.task_tracker.backend.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    private final String secretKey;
    private final SecretKey signingKey;
    @Getter
    private final int expirationTime;

    public JwtService(@Value("${jwt.secret-key}") String secretKey, @Value("${jwt.expiration-minutes}") int expirationTime) {
        this.secretKey = secretKey;
        this.signingKey = getSignInKey();
        this.expirationTime = expirationTime;
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(UserEntity userEntity) {
        long expirationTimeMs = (long) expirationTime * 60 * 1000;

        return Jwts.builder()
                .subject(userEntity.getEmail())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTimeMs))
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserEntity userEntity) {
        final String email = extractEmail(token);
        return (email.equals(userEntity.getEmail())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @PostConstruct
    private void validateConfig() {
        if (expirationTime <= 0) {
            throw new IllegalArgumentException("jwt.expiration-minutes must be > 0");
        }
    }
}

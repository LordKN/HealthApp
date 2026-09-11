package com.HealthApp.service;

import com.HealthApp.exception.InvalidRefreshTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secretKey;

    /*
    public JwtService() {
        secretKey = generateSecretKey();
    }

    public String generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA512");
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key", e);
        }
    }

 */

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 300))
                .signWith(getKey())
                .compact();

    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateRefreshToken(UUID sessionId, UUID tokenId, Instant expiresAt) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("sessionId", sessionId.toString());
        claims.put("tokenId", tokenId.toString());

        return Jwts.builder()
                .claims(claims)
                .issuedAt(new Date())
                .expiration(Date.from(expiresAt))
                .signWith(getKey())
                .compact();
    }

    public Claims extractAllClaims (String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public UUID extractSessionId (String token) {
        Claims claim = extractAllClaims(token);
        try {
            return UUID.fromString(
                    claim.get("sessionId", String.class)
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidRefreshTokenException();
        }

    }

    public UUID extractTokenId (String token) {
        Claims claim = extractAllClaims(token);
        try {
            return UUID.fromString(
                    claim.get("tokenId", String.class)
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidRefreshTokenException();
        }
    }

    public String extractUsername (String token) {
        Claims claim = extractAllClaims(token);
        return claim.getSubject();
    }
}

package com.cs319group3.backend.Components;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretString;

    @Value("${jwt.expiration:36000000}") // Default 10 hours in milliseconds
    private long jwtExpiration;

    /**
     * Generate token with username, email, userId, and userTypeId claims
     */
    public String generateToken(String username, String email, int userId, int userTypeId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);
        claims.put("userId", userId);
        claims.put("userTypeId", userTypeId);

        return generateToken(claims, username);
    }

    /**
     * Generate token with custom claims
     */
    private String generateToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSigningKey())
                .compact();
    }

    /**
     * Extract username from token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extract email from token
     */
    public String extractEmail(String token) {
        return extractClaim(token, claims -> claims.get("email", String.class));
    }

    /**
     * Extract userTypeId from token
     */
    public Integer extractUserTypeId(String token) {
        return extractClaim(token, claims -> claims.get("userTypeId", Integer.class));
    }

    /**
     * Extract userId from token
     */
    public Integer extractUserId(String token) {
        return extractClaim(token, claims -> claims.get("userId", Integer.class));
    }

    /**
     * Extract expiration date from token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Generic method to extract any claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Check if the token is valid and not expired
     */
    public boolean validateToken(String token, String username) {
        try {
            final String extractedUsername = extractUsername(token);
            return (extractedUsername.equals(username) && !isTokenExpired(token));
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validate token with detailed error handling
     */
    public boolean validateTokenWithErrors(String token) throws Exception {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (SignatureException e) {
            throw new Exception("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            throw new Exception("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new Exception("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            throw new Exception("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            throw new Exception("JWT claims string is empty");
        }
    }

    /**
     * Check if the token is expired
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extract all claims from token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Get signing key from secret
     */
    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretString);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
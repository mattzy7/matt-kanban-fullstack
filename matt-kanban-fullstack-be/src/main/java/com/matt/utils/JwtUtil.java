package com.matt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class JwtUtil {
    /**
     * Generates a JWT token.
     *
     * @param userId         the user ID that will be embedded in the token's subject
     * @param expirationInSec the expiration time of the token in seconds
     * @param jwtSecret      the secret key used to sign the token
     * @return the generated JWT token as a string
     */
    public static String generateToken(String userId, int expirationInSec, String jwtSecret) {
        LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(expirationInSec);
        Date expireDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
        SecretKey key = getSecretKey(jwtSecret);

        return Jwts.builder()
                .subject(userId)
                .issuedAt(new Date())
                .expiration(expireDate)
                .signWith(key)
                .compact();
    }

    private static SecretKey getSecretKey(String jwtSecret) {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes());
    }

    /**
     * Validates the JWT token and retrieves the subject (user ID) from it.
     *
     * @param token     the JWT token to be validated
     * @param jwtSecret the secret key used to validate the token
     * @return the userId (subject) from the token if valid
     * @throws Exception if the token is invalid or expired
     */
    public static String validateRetrieveToken(String token, String jwtSecret) throws Exception {
        SecretKey key = getSecretKey(jwtSecret);
        Jws<Claims> claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);

        // Check if the token is expired
        if (claims.getPayload().getExpiration().before(new Date())) {
            throw new ExpiredJwtException(null, null, "Token has expired");
        }
        return claims.getPayload().getSubject();
    }
}

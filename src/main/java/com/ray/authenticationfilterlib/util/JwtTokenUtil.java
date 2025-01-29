package com.ray.authenticationfilterlib.util;

import com.ray.authenticationfilterlib.exception.*;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.Key;

@Component
public class JwtTokenUtil {


    private static final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);
    @Value("${jwt.secret}") String secret;

    public Claims validateToken(String token) {
        try {
            SecretKey key = getSecretKey();
            JwtParser parser = Jwts.parser().verifyWith(key).build();
            Claims claims = parser.parseSignedClaims(token).getBody();
            return claims;
        }catch (ExpiredJwtException e) {
            logger.error("JWT token has expired: {}", token);
            throw new JwtTokenExpiredException("JWT token has expired.");
        } catch (MalformedJwtException e) {
            logger.error("Malformed JWT token: {}", token);
            throw new JwtTokenMalformedException("Malformed JWT token.");
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", token);
            throw new JwtTokenSignatureException("Invalid JWT signature.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token: {}", token);
            throw new JwtTokenUnsupportedException("Unsupported JWT token.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT token is empty or invalid: {}", token);
            throw new JwtTokenInvalidException("JWT token is empty or invalid.");
        } catch (Exception e) {
            logger.error("Unknown error while parsing JWT token: {}", token, e);
            throw new JwtTokenException("An error occurred while parsing the JWT token.");
        }
    }


    private SecretKey getSecretKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

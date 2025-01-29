package com.ray.authenticationfilterlib.filter;

import com.ray.authenticationfilterlib.util.JwtTokenUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.SignatureException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    /**
     * Filters the request and validates the JWT token.
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @param filterChain the FilterChain to pass along the request-response pair
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = getTokenFromRequest(request);

        if (token == null) {
            filterChain.doFilter(request, response);  // If no token, continue with the filter chain
            return;
        }

        try {
            var claims = jwtTokenUtil.validateToken(token);

            String username = claims.getSubject();
            String role = claims.get("role", String.class);
            List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));

            Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            logger.debug("Authentication successful for user: {}", username);

        } catch (ExpiredJwtException e) {
            logger.error("Token expired: {}", token);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
            return;
        } catch (MalformedJwtException e) {
            logger.error("Malformed token: {}", token);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Malformed token");
            return;
        }
         catch (Exception e) {
            logger.error("Authentication failed for token: {}", token, e);
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed");
            return;
        }

        filterChain.doFilter(request, response);  // Continue the filter chain after authentication
    }

    /**
     * Extracts the token from the HTTP request header.
     * @param request the HttpServletRequest
     * @return the token if found, null otherwise
     */
    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("authtoken");

        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);  // Remove "Bearer " prefix
        }

        return null;  // Return null if token is not present or does not start with "Bearer "
    }
}

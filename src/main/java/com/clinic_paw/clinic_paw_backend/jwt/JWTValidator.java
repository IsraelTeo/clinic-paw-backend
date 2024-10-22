package com.clinic_paw.clinic_paw_backend.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;

@Component
public class JWTValidator extends OncePerRequestFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(JWTValidator.class);

    private final JWTUtil jwtUtil;

    @Autowired
    public JWTValidator(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        LOGGER.info("Received request at: {}", request.getRequestURI());

        if (jwtToken != null) {
            LOGGER.info("JWT Token received: {}", jwtToken);
            jwtToken = jwtToken.substring(7);
            LOGGER.debug("Extracted JWT Token (without 'Bearer'): {}", jwtToken);
            try {
                DecodedJWT decodedJWT = jwtUtil.verifyToken(jwtToken);
                LOGGER.debug("JWT verified successfully");

                String username = jwtUtil.extractUsername(decodedJWT);
                LOGGER.debug("Extracted Username from JWT: {}", username);

                String stringAuthorities = jwtUtil.getSpecificClaim(decodedJWT, "authorities").asString();
                LOGGER.debug("Authorities extracted from JWT: {}", stringAuthorities);

                Collection<? extends GrantedAuthority> authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(stringAuthorities);
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
                context.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(context);

                LOGGER.debug("Authentication successfully set for user: {}", username);

            } catch (Exception e) {
                LOGGER.error("Error verifying JWT token: {}", e.getMessage());
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT token is invalid");
            }
        }else {
            LOGGER.warn("Authorization header is missing or invalid");
        }
        filterChain.doFilter(request, response);
    }
}

package com.clinic_paw.clinic_paw_backend.auth;

import com.clinic_paw.clinic_paw_backend.dto.AuthLoginRequest;
import com.clinic_paw.clinic_paw_backend.dto.AuthResponse;
import com.clinic_paw.clinic_paw_backend.jwt.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    private final JWTUtil jwtUtil;

    private final AuthenticateService authenticateService;

    @Autowired
    public LoginService(JWTUtil jwtUtil, AuthenticateService authenticateService) {
        this.jwtUtil = jwtUtil;
        this.authenticateService = authenticateService;
    }

    public AuthResponse login(AuthLoginRequest request){
        String username = request.username();
        String password = request.password();
        LOGGER.info("Login attempt for user: {}", username);
        try {
            Authentication authentication = authenticateService.authenticate(username, password);
            LOGGER.info("User authenticated successfully: {}", username);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String accessToken = jwtUtil.generateToken(authentication);
            LOGGER.info("Access token generated for user: {}", username);

            return AuthResponse.builder()
                    .username(username)
                    .message("User logged in successfully")
                    .jwt(accessToken)
                    .status(true)
                    .build();
        } catch (Exception e) {
            LOGGER.error("Login failed for user {}: {}", username, e.getMessage());
            return AuthResponse.builder()
                    .username(username)
                    .message("Login failed")
                    .jwt(null)
                    .status(false)
                    .build();
        }
    }
}

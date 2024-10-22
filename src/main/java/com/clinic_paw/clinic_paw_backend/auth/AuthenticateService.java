package com.clinic_paw.clinic_paw_backend.auth;

import com.clinic_paw.clinic_paw_backend.exception.ApiError;
import com.clinic_paw.clinic_paw_backend.exception.PawException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateService.class);

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticateService(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public Authentication authenticate(String username, String password) {
        LOGGER.debug("Authenticating user: {}", username);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if (userDetails == null) {
            LOGGER.error("Authentication failed: User not found");
            throw new PawException(ApiError.BAD_CREDENTIALS);
        }

        LOGGER.debug("User authenticated successfully: {}", username);

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            LOGGER.error("Authentication failed: Incorrect password for user: {}", username);
            throw new PawException(ApiError.INCORRECT_PASSWORD);
        }

        LOGGER.debug("Password verified successfully for user: {}", username);

        return new UsernamePasswordAuthenticationToken(
                username,
                password,
                userDetails.getAuthorities()
        );
    }
}

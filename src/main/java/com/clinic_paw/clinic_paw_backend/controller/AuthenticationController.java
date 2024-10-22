package com.clinic_paw.clinic_paw_backend.controller;

import com.clinic_paw.clinic_paw_backend.auth.LoginService;
import com.clinic_paw.clinic_paw_backend.auth.RegisterUserService;
import com.clinic_paw.clinic_paw_backend.dto.AuthCreateUserAdminRequest;
import com.clinic_paw.clinic_paw_backend.dto.AuthLoginRequest;
import com.clinic_paw.clinic_paw_backend.dto.AuthResponse;
import com.clinic_paw.clinic_paw_backend.exception.SecurityErrorHandler;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.method.HandleAuthorizationDenied;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final LoginService loginService;

    private final RegisterUserService registerUserService;

    @Autowired
    public AuthenticationController(LoginService loginService, RegisterUserService registerUserService) {
        this.loginService = loginService;
        this.registerUserService = registerUserService;
    }

    @PostMapping("/login")
    @HandleAuthorizationDenied(handlerClass = SecurityErrorHandler.class)
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.loginService.login(userRequest), HttpStatus.CREATED);
    }

    @PostMapping("/sign-up")
    @HandleAuthorizationDenied(handlerClass = SecurityErrorHandler.class)
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserAdminRequest registerRequest) {
        return new ResponseEntity<>(registerUserService.createUserAdmin(registerRequest), HttpStatus.CREATED);
    }
}

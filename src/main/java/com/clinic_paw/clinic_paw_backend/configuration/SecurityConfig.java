package com.clinic_paw.clinic_paw_backend.configuration;

import com.clinic_paw.clinic_paw_backend.auth.UserDetailsServiceImpl;
import com.clinic_paw.clinic_paw_backend.jwt.JWTUtil;
import com.clinic_paw.clinic_paw_backend.jwt.JWTValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authorization.method.AuthorizationAdvisorProxyFactory;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JWTUtil jwtUtil;

    @Autowired
    public SecurityConfig(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(httpSecuritySessionManagement ->
                        httpSecuritySessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    //auth
                    http.requestMatchers(HttpMethod.POST, "/auth/**").permitAll();
                    //employee
                    http.requestMatchers(HttpMethod.POST, "/api/v1/employee").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/v1/employee").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/employee/{id}").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET , "/api/v1/employee/{dni}").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.GET , "/api/v1/employees").hasRole("ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/v1/employee/{dni}").hasRole("ADMIN");
                    //customer
                    http.requestMatchers(HttpMethod.POST, "/api/v1/customer").hasAnyRole("EMPLOYEE", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/v1/customer").hasAnyRole("EMPLOYEE", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/customer/{id}").hasAnyRole("EMPLOYEE", "ADMIN");
                    http.requestMatchers(HttpMethod.GET,  "/api/v1/customer/{dni}").hasAnyRole("EMPLOYEE", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/customers").hasAnyRole("EMPLOYEE", "ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/v1/customer/{id}").hasAnyRole("EMPLOYEE", "ADMIN");
                    //pet
                    http.requestMatchers(HttpMethod.POST, "/api/v1/pet").hasAnyRole("EMPLOYEE", "ADMIN");
                    http.requestMatchers(HttpMethod.PUT, "/api/v1/pet").hasAnyRole("EMPLOYEE", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/pet/{id}").hasAnyRole("EMPLOYEE", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/pet/{name}").hasAnyRole("EMPLOYEE", "ADMIN");
                    http.requestMatchers(HttpMethod.GET, "/api/v1/pets").hasAnyRole("EMPLOYEE", "ADMIN");
                    http.requestMatchers(HttpMethod.DELETE, "/api/v1/pet/{id}").hasAnyRole("EMPLOYEE", "ADMIN");
                    //default
                    http.anyRequest().denyAll();
                })
                .addFilterBefore(new JWTValidator(this.jwtUtil), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsServiceImpl) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsServiceImpl);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public Customizer<AuthorizationAdvisorProxyFactory> skipValueTypes() {
        return (factory) -> {
            factory.setTargetVisitor(AuthorizationAdvisorProxyFactory.TargetVisitor.defaultsSkipValueTypes());
        };
    }


}

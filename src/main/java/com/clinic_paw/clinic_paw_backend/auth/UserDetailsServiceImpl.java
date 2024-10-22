package com.clinic_paw.clinic_paw_backend.auth;

import com.clinic_paw.clinic_paw_backend.exception.ApiError;
import com.clinic_paw.clinic_paw_backend.exception.PawException;
import com.clinic_paw.clinic_paw_backend.model.UserEntity;
import com.clinic_paw.clinic_paw_backend.repository.IUserEntityRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final IUserEntityRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(IUserEntityRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LOGGER.debug("Attempting to load user by username: {}", username);
        UserEntity userEntity = userRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> {
                    LOGGER.error("User not found: {}", username);
                    return new PawException(ApiError.USERNAME_NOT_FOUND);
                });

        LOGGER.debug("User found: {}", userEntity.getUsername());
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        userEntity.getRoles()
                .forEach(role -> {
                    authorityList.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()));
                    LOGGER.debug("Assigned role to user: {}", role.getRoleEnum().name());
                });

        userEntity.getRoles().stream()
                .flatMap(role -> role.getPermissionsList().stream())
                .forEach(permission -> {
                    authorityList.add(new SimpleGrantedAuthority(permission.getName()));
                    LOGGER.debug("Assigned permission to user: {}", permission.getName());
                });
        LOGGER.debug("User details loaded successfully for: {}", username);

        return new User(
                userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getEnabled(),
                userEntity.getAccountNonExpired(),
                userEntity.getCredentialsNonExpired(),
                userEntity.getAccountNonLocked(),
                authorityList);
    }
}

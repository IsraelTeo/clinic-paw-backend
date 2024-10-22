package com.clinic_paw.clinic_paw_backend.auth;

import com.clinic_paw.clinic_paw_backend.dto.AuthCreateUserAdminRequest;
import com.clinic_paw.clinic_paw_backend.dto.AuthResponse;
import com.clinic_paw.clinic_paw_backend.enums.UserRoleEnum;
import com.clinic_paw.clinic_paw_backend.exception.ApiError;
import com.clinic_paw.clinic_paw_backend.exception.PawException;
import com.clinic_paw.clinic_paw_backend.jwt.JWTUtil;
import com.clinic_paw.clinic_paw_backend.model.UserEntity;
import com.clinic_paw.clinic_paw_backend.model.UserRoleEntity;
import com.clinic_paw.clinic_paw_backend.repository.IUserEntityRepository;
import com.clinic_paw.clinic_paw_backend.repository.IUserRoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
public class RegisterUserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RegisterUserService.class);

    private final IUserRoleRepository roleRepository;

    private final JWTUtil jwtUtil;

    private final IUserEntityRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterUserService(IUserRoleRepository roleRepository, JWTUtil jwtUtil, IUserEntityRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse createUserAdmin(AuthCreateUserAdminRequest authCreateUserAdminRequest) {
        LOGGER.info("Starting user admin creation with request: {}", authCreateUserAdminRequest);
        String username = authCreateUserAdminRequest.username();
        String email = authCreateUserAdminRequest.email();
        String password = authCreateUserAdminRequest.password();
        List<String> roleRequest = authCreateUserAdminRequest.roleRequest().roleListName();
        findOrCreateAdminRole();
        Set<UserRoleEntity> roles = getValidRoles(roleRequest);
        UserEntity userCreated = createAndSaveUserEntity(username, email, password, roles);
        String accessToken = generateTokenForUser(userCreated);

        return new AuthResponse(
                userCreated.getUsername(),
                "User created successfully",
                accessToken,
                true);
    }

    private UserRoleEntity findOrCreateAdminRole() {
        LOGGER.info("Finding or creating the ADMIN role");
        return roleRepository.findByRoleEnum(UserRoleEnum.ADMIN)
                .orElseGet(() -> {
                    LOGGER.warn("ADMIN role not found, creating a new one");
                    UserRoleEntity newRole = UserRoleEntity.builder()
                            .roleEnum(UserRoleEnum.ADMIN)
                            .build();
                    return roleRepository.save(newRole);
                });
    }

    private Set<UserRoleEntity> getValidRoles(List<String> roleRequest) {
        LOGGER.info("Validating and fetching roles for the request: {}", roleRequest);
        Set<UserRoleEntity> roles = new HashSet<>(roleRepository.findRoleEntitiesByRoleEnumIn(roleRequest));
        if (roles.isEmpty()) {
            LOGGER.error("No roles found for the requested list: {}", roleRequest);
            throw new PawException(ApiError.ROLE_NOT_FOUND);
        }
        return roles;
    }

    private UserEntity createAndSaveUserEntity(String username, String email, String password, Set<UserRoleEntity> roles) {
        LOGGER.info("Building UserEntity for the new user");
        UserEntity userEntity = UserEntity.builder()
                .username(username)
                .email(email)
                .password(passwordEncoder.encode(password))
                .roles(roles)
                .enabled(true)
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .build();
        LOGGER.info("Saving new user to the database");
        return userRepository.save(userEntity);
    }

    private String generateTokenForUser(UserEntity userCreated) {
        LOGGER.info("Generating JWT token for user: {}", userCreated.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userCreated.getUsername(),
                userCreated.getPassword(),
                getAuthorities(userCreated)
        );
        return jwtUtil.generateToken(authentication);
    }

    private Set<SimpleGrantedAuthority> getAuthorities(UserEntity userCreated) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        userCreated.getRoles().forEach(roleEntity ->
                authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getRoleEnum().name())));

        userCreated.getRoles().stream()
                .flatMap(roleEntity -> roleEntity.getPermissionsList() != null ? roleEntity.getPermissionsList().stream() : Stream.empty())
                .forEach(permissionEntity ->
                        authorities.add(new SimpleGrantedAuthority(permissionEntity.getName())));

        return authorities;
    }
}

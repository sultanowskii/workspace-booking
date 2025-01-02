package org.wb.components.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.wb.components.auth.dto.*;
import org.wb.components.user.User;
import org.wb.components.user.UserService;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public JwtAuthResponseDto signUp(SignUpRequestDto request) {
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(User.Role.EMPLOYEE);

        var createdUser = userService.create(user);

        if (request.getRole().equals(User.Role.ADMIN)) {
            if (userService.canAdminBeCreatedWithoutApplication()) {
                user.setRole(User.Role.ADMIN);
            }
        }

        var jwt = jwtService.generateToken(createdUser);
        return new JwtAuthResponseDto(user, jwt);
    }

    @Transactional
    public JwtAuthResponseDto signIn(SignInRequestDto request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthResponseDto(userService.getByUsername(user.getUsername()), jwt);
    }
}

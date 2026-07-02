package ems_backend.service.impl;

import ems_backend.dto.AuthResponseDto;
import ems_backend.dto.LoginDto;
import ems_backend.dto.RegisterDto;
import ems_backend.entity.User;
import ems_backend.repository.UserRepository;
import ems_backend.service.AuthService;
import ems_backend.config.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto register(RegisterDto registerDto) {
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        User user = User.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .role(registerDto.getRole())
                .build();

        userRepository.save(user);
        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDto(jwtToken);
    }

    @Override
    public AuthResponseDto login(LoginDto loginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(),
                        loginDto.getPassword()
                )
        );

        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        return new AuthResponseDto(jwtToken);
    }
}

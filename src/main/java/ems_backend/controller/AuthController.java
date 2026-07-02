package ems_backend.controller;

import ems_backend.dto.AuthResponseDto;
import ems_backend.dto.LoginDto;
import ems_backend.dto.RegisterDto;
import ems_backend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterDto registerDto) {
        return ResponseEntity.ok(authService.register(registerDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.login(loginDto));
    }
}

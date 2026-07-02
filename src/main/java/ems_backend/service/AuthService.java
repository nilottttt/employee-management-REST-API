package ems_backend.service;

import ems_backend.dto.AuthResponseDto;
import ems_backend.dto.LoginDto;
import ems_backend.dto.RegisterDto;

public interface AuthService {
    AuthResponseDto register(RegisterDto registerDto);
    AuthResponseDto login(LoginDto loginDto);
}

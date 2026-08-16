package com.example.userService.user.application.port.in;

import com.example.userService.user.application.dto.request.LoginRequest;
import com.example.userService.user.application.dto.request.RegisterRequest;
import com.example.userService.user.application.dto.response.AuthResponse;

public interface AuthUseCase {
  AuthResponse register(RegisterRequest request);

  AuthResponse login(LoginRequest request);

  AuthResponse refreshToken(String refreshToken);
}

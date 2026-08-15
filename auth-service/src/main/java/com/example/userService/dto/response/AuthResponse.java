package com.example.userService.dto.response;

import com.example.userService.enums.Role;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

  private String accessToken;
  private String refreshToken;
  private String tokenType = "Bearer";
  private UUID userId;
  private String email;
  private Role role;
}

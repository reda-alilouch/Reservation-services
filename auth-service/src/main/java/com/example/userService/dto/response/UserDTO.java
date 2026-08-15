package com.example.userService.dto.response;

import com.example.userService.enums.Role;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

  private UUID id;
  private String firstName;
  private String lastName;
  private String email;
  private Role role;
  private Boolean isActive;
  private LocalDateTime createdAt;
}

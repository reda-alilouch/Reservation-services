package com.example.userService.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {

  @NotBlank(message = "L'ancien mot de passe est obligatoire")
  private String oldPassword;

  @Size(min = 8, message = "Le nouveau mot de passe doit avoir au moins 8 caractères")
  @NotBlank(message = "Le nouveau mot de passe est obligatoire")
  private String newPassword;

  @NotBlank(message = "La confirmation est obligatoire")
  private String confirmPassword;
}

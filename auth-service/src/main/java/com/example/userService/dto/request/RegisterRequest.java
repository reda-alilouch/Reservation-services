package com.example.userService.dto.request;

import com.example.userService.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Le prénom est obligatoire")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    private String lastName;

    @Email(message = "Email invalide")
    @NotBlank(message = "L'email est obligatoire")
    private String email;

    @Size(min = 8, message = "Le mot de passe doit avoir au moins 8 caractères")
    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;

    private Role role = Role.CLIENT;
}

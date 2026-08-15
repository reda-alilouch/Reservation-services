package com.example.userService.user.application.port.in;

import com.example.userService.dto.request.ChangePasswordRequest;
import com.example.userService.dto.request.UpdateProfileRequest;
import com.example.userService.dto.response.UserDTO;
import java.util.List;
import java.util.UUID;

public interface UserUseCase {
  UserDTO findById(UUID id);

  UserDTO findByEmail(String email);

  UserDTO updateProfile(UUID id, UpdateProfileRequest request);

  void changePassword(UUID id, ChangePasswordRequest request);

  List<UserDTO> findAll();

  void updateUserStatus(UUID id, boolean active);
}

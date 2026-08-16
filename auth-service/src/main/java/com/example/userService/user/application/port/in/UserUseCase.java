package com.example.userService.user.application.port.in;

import java.util.List;
import java.util.UUID;

import com.example.userService.user.application.dto.request.ChangePasswordRequest;
import com.example.userService.user.application.dto.request.UpdateProfileRequest;
import com.example.userService.user.application.dto.response.UserDTO;

public interface UserUseCase {
  UserDTO findById(UUID id);

  UserDTO findByEmail(String email);

  UserDTO updateProfile(UUID id, UpdateProfileRequest request);

  void changePassword(UUID id, ChangePasswordRequest request);

  List<UserDTO> findAll();

  void updateUserStatus(UUID id, boolean active);
}

package com.example.hotel_service.room.application.port.in;

import com.example.hotel_service.room.application.dto.request.CreateRoomRequest;
import com.example.hotel_service.room.application.dto.request.UpdateRoomRequest;
import com.example.hotel_service.room.application.dto.response.RoomResponse;
import java.util.List;
import java.util.UUID;

public interface RoomUseCase {
  List<RoomResponse> findAll();

  RoomResponse findById(UUID id);

  RoomResponse create(CreateRoomRequest request);

  RoomResponse update(UUID id, UpdateRoomRequest request);

  void delete(UUID id);
}

package com.example.hotel_service.room.application.port.out;

import com.example.hotel_service.entity.Room;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoomPersistencePort {
    List<Room> findAll();
    Optional<Room> findById(UUID id);
    Room save(Room room);
    void delete(Room room);
}

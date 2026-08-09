package com.example.hotel_service.room.infrastructure.persistence;

import com.example.hotel_service.entity.Room;
import com.example.hotel_service.room.application.port.out.RoomPersistencePort;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class RoomPersistenceAdapter implements RoomPersistencePort {
    private final JpaRoomRepository repository;
    public RoomPersistenceAdapter(JpaRoomRepository repository) { this.repository = repository; }
    public List<Room> findAll() { return repository.findAll(); }
    public Optional<Room> findById(UUID id) { return repository.findById(id); }
    public Room save(Room room) { return repository.save(room); }
    public void delete(Room room) { repository.delete(room); }
}

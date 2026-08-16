package com.example.hotel_service.room.infrastructure.persistence;

import com.example.hotel_service.entity.Room;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface JpaRoomRepository extends JpaRepository<Room, UUID> {}

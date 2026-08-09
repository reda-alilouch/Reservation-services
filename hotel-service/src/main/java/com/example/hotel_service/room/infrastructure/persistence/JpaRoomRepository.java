package com.example.hotel_service.room.infrastructure.persistence;

import com.example.hotel_service.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

interface JpaRoomRepository extends JpaRepository<Room, UUID> { }

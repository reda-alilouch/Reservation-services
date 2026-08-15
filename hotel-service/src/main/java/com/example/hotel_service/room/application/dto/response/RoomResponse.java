package com.example.hotel_service.room.application.dto.response;

import com.example.hotel_service.enums.RoomType;
import java.math.BigDecimal;
import java.util.UUID;

public record RoomResponse(
    UUID id,
    UUID hotelId,
    String roomNumber,
    String name,
    String description,
    RoomType roomType,
    Integer capacity,
    Integer bedCount,
    String bedType,
    BigDecimal pricePerNight,
    Integer totalRooms,
    Integer availableRooms) {}

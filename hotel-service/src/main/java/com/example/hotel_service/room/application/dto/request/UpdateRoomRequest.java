package com.example.hotel_service.room.application.dto.request;

import com.example.hotel_service.enums.RoomType;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record UpdateRoomRequest(
    @NotBlank @Size(max = 50) String roomNumber,
    @NotBlank @Size(max = 255) String name,
    String description,
    @NotNull RoomType roomType,
    @NotNull @Positive Integer capacity,
    @NotNull @Positive Integer bedCount,
    @Size(max = 100) String bedType,
    @NotNull @Positive BigDecimal pricePerNight,
    @NotNull @Positive Integer totalRooms,
    @NotNull @PositiveOrZero Integer availableRooms) {}

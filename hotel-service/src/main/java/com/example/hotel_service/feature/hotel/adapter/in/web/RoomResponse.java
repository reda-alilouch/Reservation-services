package com.example.hotel_service.feature.hotel.adapter.in.web;

import com.example.hotel_service.feature.hotel.application.view.RoomSummary;
import java.math.BigDecimal;
import java.util.UUID;

public record RoomResponse(UUID id, String type, Integer capacity, BigDecimal pricePerNight) {
    static RoomResponse from(RoomSummary room) { return new RoomResponse(room.id(), room.type(), room.capacity(), room.pricePerNight()); }
}

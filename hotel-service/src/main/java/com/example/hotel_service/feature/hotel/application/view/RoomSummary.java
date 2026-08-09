package com.example.hotel_service.feature.hotel.application.view;

import java.math.BigDecimal;
import java.util.UUID;

public record RoomSummary(UUID id, String type, Integer capacity, BigDecimal pricePerNight) { }

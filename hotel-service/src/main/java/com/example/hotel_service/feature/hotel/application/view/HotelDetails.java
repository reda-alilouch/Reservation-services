package com.example.hotel_service.feature.hotel.application.view;

import com.example.hotel_service.feature.hotel.domain.model.HotelStatus;
import java.util.List;
import java.util.UUID;

public record HotelDetails(UUID id, String name, String description, String city,
                           String address, HotelStatus status, List<RoomSummary> rooms) { }

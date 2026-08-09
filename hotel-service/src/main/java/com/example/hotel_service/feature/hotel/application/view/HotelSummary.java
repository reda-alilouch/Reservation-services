package com.example.hotel_service.feature.hotel.application.view;

import com.example.hotel_service.feature.hotel.domain.model.HotelStatus;
import java.util.UUID;

public record HotelSummary(UUID id, String name, String city, String address, HotelStatus status) { }

package com.example.hotel_service.hotel.application.dto.response;
import com.example.hotel_service.enums.HotelStatus;
import java.util.UUID;
public record HotelResponse(UUID id, UUID providerId, String name, String description, String address, String city, String country, HotelStatus status) { }

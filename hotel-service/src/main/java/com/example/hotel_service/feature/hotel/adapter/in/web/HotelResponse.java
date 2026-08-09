package com.example.hotel_service.feature.hotel.adapter.in.web;

import com.example.hotel_service.feature.hotel.application.view.HotelSummary;
import java.util.UUID;

public record HotelResponse(UUID id, String name, String city, String address, String status) {
    static HotelResponse from(HotelSummary hotel) {
        return new HotelResponse(hotel.id(), hotel.name(), hotel.city(), hotel.address(), hotel.status().name());
    }
}

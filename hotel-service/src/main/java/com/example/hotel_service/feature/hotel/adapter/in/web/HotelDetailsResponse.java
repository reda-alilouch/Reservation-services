package com.example.hotel_service.feature.hotel.adapter.in.web;

import com.example.hotel_service.feature.hotel.application.view.HotelDetails;
import java.util.List;
import java.util.UUID;

public record HotelDetailsResponse(UUID id, String name, String description, String city, String address,
                                   String status, List<RoomResponse> rooms) {
    static HotelDetailsResponse from(HotelDetails hotel) {
        return new HotelDetailsResponse(hotel.id(), hotel.name(), hotel.description(), hotel.city(), hotel.address(),
                hotel.status().name(), hotel.rooms().stream().map(RoomResponse::from).toList());
    }
}

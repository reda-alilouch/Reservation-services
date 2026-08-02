package com.example.hotel_service.mapper;

import com.example.hotel_service.dto.response.HotelResponse;
import com.example.hotel_service.entity.Hotel;

import java.util.List;
import java.util.UUID;

public class HotelMapper {


        public static HotelResponse toResponse(
                Hotel hotel
        ) {

            return HotelResponse.builder()
                    .id(hotel.getId())
                    .name(hotel.getName())
                    .city(hotel.getCity())
                    .address(hotel.getAddress())
                    .status(hotel.getStatus())
                    .build();
    }
}

package com.example.hotel_service.dto.response;

import com.example.hotel_service.enums.HotelStatus;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
public class HotelDetailsResponse {

    private UUID id;

    private String name;

    private String description;

    private String city;

    private String address;

    private HotelStatus status;

    private List<RoomResponse> rooms;
}
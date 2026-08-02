package com.example.hotel_service.dto.request;

import lombok.*;

@Getter
@Setter
public class UpdateHotelRequest {

    private String name;

    private String description;

    private String city;

    private String address;
}

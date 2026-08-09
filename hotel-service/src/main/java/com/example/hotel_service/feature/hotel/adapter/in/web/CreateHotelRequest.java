package com.example.hotel_service.feature.hotel.adapter.in.web;

import jakarta.validation.constraints.NotBlank;

public record CreateHotelRequest(
    @NotBlank String name, 
    @NotBlank String description,  
    @NotBlank String city, 
    @NotBlank String address,                                 
    @NotBlank String country) { }

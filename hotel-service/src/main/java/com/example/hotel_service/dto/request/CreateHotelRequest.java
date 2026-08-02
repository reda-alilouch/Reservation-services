package com.example.hotel_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
public class CreateHotelRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String city;

    @NotBlank
    private String address;
}

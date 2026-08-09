package com.example.hotel_service.hotel.application.dto.request;
import jakarta.validation.constraints.*;
import java.util.UUID;
public record CreateHotelRequest(@NotNull UUID providerId, @NotBlank @Size(max=255) String name, String description, @NotBlank @Size(max=255) String address, @NotBlank @Size(max=100) String city, @NotBlank @Size(max=100) String country) { }

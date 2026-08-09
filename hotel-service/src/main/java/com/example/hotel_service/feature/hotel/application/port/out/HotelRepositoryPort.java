package com.example.hotel_service.feature.hotel.application.port.out;

import com.example.hotel_service.feature.hotel.domain.model.Hotel;
import com.example.hotel_service.feature.hotel.domain.model.HotelStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotelRepositoryPort {
    Hotel save(Hotel hotel);
    Optional<Hotel> findById(UUID id);
    List<Hotel> findByNameContainingIgnoreCase(String name);
    List<Hotel> findByStatus(HotelStatus status);
}

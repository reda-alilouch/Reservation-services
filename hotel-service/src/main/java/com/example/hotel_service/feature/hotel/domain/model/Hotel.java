package com.example.hotel_service.feature.hotel.domain.model;

import java.time.LocalDateTime;
import java.util.UUID;

/** Aggregate root. It deliberately has no dependency on Spring or JPA. */
public record Hotel(UUID id, UUID providerId, String name, String description,
                    String address, String city, String country, HotelStatus status,
                    LocalDateTime createdAt, LocalDateTime updatedAt) {

    public static Hotel pending(UUID providerId, String name, String description,
                                String address, String city, String country) {
        LocalDateTime now = LocalDateTime.now();
        return new Hotel(null, providerId, name, description, address, city, country,
                HotelStatus.PENDING, now, now);
    }

    public Hotel withStatus(HotelStatus newStatus) {
        return new Hotel(id, providerId, name, description, address, city, country,
                newStatus, createdAt, LocalDateTime.now());
    }
}

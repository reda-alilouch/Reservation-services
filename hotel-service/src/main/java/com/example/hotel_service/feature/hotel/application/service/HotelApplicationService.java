package com.example.hotel_service.feature.hotel.application.service;

import com.example.hotel_service.feature.hotel.application.port.in.CreateHotelUseCase;
import com.example.hotel_service.feature.hotel.application.port.in.GetHotelUseCase;
import com.example.hotel_service.feature.hotel.application.port.in.ReviewHotelUseCase;
import com.example.hotel_service.feature.hotel.application.port.out.HotelRepositoryPort;
import com.example.hotel_service.feature.hotel.application.view.HotelDetails;
import com.example.hotel_service.feature.hotel.application.view.HotelSummary;
import com.example.hotel_service.feature.hotel.application.view.RoomSummary;
import com.example.hotel_service.feature.hotel.domain.model.Hotel;
import com.example.hotel_service.feature.hotel.domain.model.HotelStatus;
import com.example.hotel_service.shared.domain.ResourceNotFoundException;

import java.util.List;
import java.util.UUID;

public class HotelApplicationService implements CreateHotelUseCase, GetHotelUseCase, ReviewHotelUseCase {
    private final HotelRepositoryPort hotels;

    public HotelApplicationService(HotelRepositoryPort hotels) { this.hotels = hotels; }

    @Override public HotelSummary create(CreateHotelCommand command) {
        Hotel saved = hotels.save(Hotel.pending(command.providerId(), command.name(), command.description(),
                command.address(), command.city(), command.country()));
        return summary(saved);
    }

    @Override public HotelDetails getById(UUID id) {
        Hotel hotel = required(id);
        return new HotelDetails(hotel.id(), hotel.name(), hotel.description(), hotel.city(), hotel.address(),
                hotel.status(), List.<RoomSummary>of());
    }

    @Override public List<HotelSummary> getByName(String name) {
        return hotels.findByNameContainingIgnoreCase(name).stream().map(HotelApplicationService::summary).toList();
    }

    @Override public List<HotelSummary> getApproved() {
        return hotels.findByStatus(HotelStatus.APPROVED).stream().map(HotelApplicationService::summary).toList();
    }

    @Override public HotelSummary approve(UUID id) { return summary(hotels.save(required(id).withStatus(HotelStatus.APPROVED))); }
    @Override public HotelSummary reject(UUID id, String reason) { return summary(hotels.save(required(id).withStatus(HotelStatus.REJECTED))); }

    private Hotel required(UUID id) {
        return hotels.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hotel not found: " + id));
    }
    private static HotelSummary summary(Hotel hotel) {
        return new HotelSummary(hotel.id(), hotel.name(), hotel.city(), hotel.address(), hotel.status());
    }
}

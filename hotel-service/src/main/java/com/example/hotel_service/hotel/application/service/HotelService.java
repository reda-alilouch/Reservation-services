package com.example.hotel_service.hotel.application.service;

import com.example.hotel_service.entity.Hotel;
import com.example.hotel_service.hotel.application.dto.request.*;
import com.example.hotel_service.hotel.application.dto.response.HotelResponse;
import com.example.hotel_service.hotel.application.port.in.HotelUseCase;
import com.example.hotel_service.hotel.application.port.out.HotelPersistencePort;
import com.example.hotel_service.shared.exception.ResourceNotFoundException;
import java.util.*;

public class HotelService implements HotelUseCase {

  private final HotelPersistencePort hotels;

  public HotelService(HotelPersistencePort hotels) {
    this.hotels = hotels;
  }

  public List<HotelResponse> findAll() {
    return hotels.findAll().stream().map(this::toResponse).toList();
  }

  public HotelResponse findById(UUID id) {
    return toResponse(entity(id));
  }

  public HotelResponse create(CreateHotelRequest r) {
    Hotel h = new Hotel();
    h.setProviderId(r.providerId());
    apply(h, r.name(), r.description(), r.address(), r.city(), r.country());
    return toResponse(hotels.save(h));
  }

  public HotelResponse update(UUID id, UpdateHotelRequest r) {
    Hotel h = entity(id);
    apply(h, r.name(), r.description(), r.address(), r.city(), r.country());
    return toResponse(hotels.save(h));
  }

  public void archive(UUID id) {
    Hotel hotel = entity(id);
    hotel.archive();
    hotels.save(hotel);
  }

  private Hotel entity(UUID id) {
    return hotels
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found: " + id));
  }

  private void apply(Hotel h, String n, String d, String a, String c, String co) {
    h.setName(n);
    h.setDescription(d);
    h.setAddress(a);
    h.setCity(c);
    h.setCountry(co);
  }

  private HotelResponse toResponse(Hotel h) {
    return new HotelResponse(
        h.getId(),
        h.getProviderId(),
        h.getName(),
        h.getDescription(),
        h.getAddress(),
        h.getCity(),
        h.getCountry(),
        h.getStatus());
  }
}

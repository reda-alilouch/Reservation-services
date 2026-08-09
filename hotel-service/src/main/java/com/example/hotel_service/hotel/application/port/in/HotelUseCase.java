package com.example.hotel_service.hotel.application.port.in;

import com.example.hotel_service.hotel.application.dto.request.CreateHotelRequest;
import com.example.hotel_service.hotel.application.dto.request.UpdateHotelRequest;
import com.example.hotel_service.hotel.application.dto.response.HotelResponse;
import java.util.List;
import java.util.UUID;

public interface HotelUseCase {
    List<HotelResponse> findAll();
    HotelResponse findById(UUID id);
    HotelResponse create(CreateHotelRequest request);
    HotelResponse update(UUID id, UpdateHotelRequest request);
    void delete(UUID id);
}

package com.example.hotel_service.feature.hotel.application.port.in;

import com.example.hotel_service.feature.hotel.application.view.HotelDetails;
import com.example.hotel_service.feature.hotel.application.view.HotelSummary;
import java.util.List;
import java.util.UUID;

public interface GetHotelUseCase {
    HotelDetails getById(UUID id);
    List<HotelSummary> getByName(String name);
    List<HotelSummary> getApproved();
}

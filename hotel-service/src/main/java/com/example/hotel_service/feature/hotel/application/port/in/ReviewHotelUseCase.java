package com.example.hotel_service.feature.hotel.application.port.in;

import com.example.hotel_service.feature.hotel.application.view.HotelSummary;
import java.util.UUID;

public interface ReviewHotelUseCase {
    HotelSummary approve(UUID id);
    HotelSummary reject(UUID id, String reason);
}

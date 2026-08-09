package com.example.hotel_service.feature.hotel.application.port.in;

import com.example.hotel_service.feature.hotel.application.view.HotelSummary;
import java.util.UUID;

public interface CreateHotelUseCase {
    HotelSummary create(CreateHotelCommand command);

    record CreateHotelCommand(UUID providerId, String name, String description,
                              String address, String city, String country) { }
}

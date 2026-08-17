package com.example.hotel_service.hotel.application.port.out;

import com.example.hotel_service.entity.Hotel;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface HotelPersistencePort {
  List<Hotel> findAll();

  Optional<Hotel> findById(UUID id);

  Hotel save(Hotel hotel);

}

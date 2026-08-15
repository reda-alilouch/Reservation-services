package com.example.hotel_service.hotel.infrastructure.persistence;

import com.example.hotel_service.entity.Hotel;
import java.util.UUID;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


interface JpaHotelRepository extends JpaRepository<Hotel, UUID> {
    List<Hotel> findAll();

    Optional<Hotel> findById(UUID id);
    
    Hotel save(Hotel hotel);

}

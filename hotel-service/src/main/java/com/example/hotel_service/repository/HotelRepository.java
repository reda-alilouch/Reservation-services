package com.example.hotel_service.repository;

import com.example.hotel_service.entity.Hotel;
import com.example.hotel_service.enums.HotelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, UUID> {

    List<Hotel> findByAll();

    List<Hotel> findByStatus(HotelStatus status);

    List<Hotel> findByCity(String city);

    List<Hotel> findByStatusAndCity(HotelStatus status, String city);

    List<Hotel> findByStatusAndCityAndNameContainingIgnoreCase(HotelStatus status, String city, String name);
}


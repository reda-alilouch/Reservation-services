package com.example.hotel_service.hotel.infrastructure.persistence;

import com.example.hotel_service.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

interface JpaHotelRepository extends JpaRepository<Hotel, UUID> { }

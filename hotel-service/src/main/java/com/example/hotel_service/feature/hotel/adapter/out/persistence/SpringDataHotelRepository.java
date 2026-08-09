package com.example.hotel_service.feature.hotel.adapter.out.persistence;

import com.example.hotel_service.feature.hotel.domain.model.HotelStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

interface SpringDataHotelRepository extends JpaRepository<HotelJpaEntity, UUID> {
    List<HotelJpaEntity> findByNameContainingIgnoreCase(String name);
    List<HotelJpaEntity> findByStatus(HotelStatus status);
}

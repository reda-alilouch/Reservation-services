package com.example.hotel_service.feature.hotel.adapter.out.persistence;

import com.example.hotel_service.feature.hotel.domain.model.HotelStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "hotels", indexes = {
        @Index(name = "idx_hotels_provider_id", columnList = "provider_id"),
        @Index(name = "idx_hotels_status", columnList = "status"),
        @Index(name = "idx_hotels_city", columnList = "city")})
@Getter 
@Setter 
@NoArgsConstructor
public class HotelJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID) 
    private UUID id;

    @Column(name = "provider_id", nullable = false) 
    private UUID providerId;

    @Column(nullable = false, length = 255) 
    private String name;

    @Column(columnDefinition = "TEXT") 
    private String description;

    @Column(nullable = false, length = 255) 
    private String address;

    @Column(nullable = false, length = 100) 
    private String city;

    @Column(nullable = false, length = 100) 
    private String country;

    @Enumerated(EnumType.STRING) @Column(nullable = false, length = 20) 
    private HotelStatus status;

    @Column(name = "created_at", nullable = false, updatable = false) 
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false) 
    private LocalDateTime updatedAt;
}

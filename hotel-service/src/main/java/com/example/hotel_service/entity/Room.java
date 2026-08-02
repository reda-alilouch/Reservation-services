package com.example.hotel_service.entity;

import com.example.hotel_service.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="rooms")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="hotel_id")
    private Hotel hotel;

    @Enumerated(EnumType.STRING)
    private RoomType type;

    private Integer capacity;

    private BigDecimal pricePerNight;

    private Integer availableCount;
}

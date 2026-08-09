package com.example.hotel_service.feature.room.adapter.out.persistence;

import com.example.hotel_service.feature.hotel.adapter.out.persistence.HotelJpaEntity;
import com.example.hotel_service.feature.room.domain.model.RoomType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/** JPA representation; it is deliberately isolated from the room domain. */
@Entity
@Table(name = "rooms", indexes = {@Index(name = "idx_rooms_hotel_id", columnList = "hotel_id"), @Index(name = "idx_rooms_type", columnList = "room_type")})
@Getter @Setter @NoArgsConstructor
class RoomJpaEntity {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "hotel_id", nullable = false, foreignKey = @ForeignKey(name = "fk_room_hotel")) private HotelJpaEntity hotel;
    @Column(name = "room_number", nullable = false, length = 50) private String roomNumber;
    @Column(nullable = false, length = 255) private String name;
    @Column(columnDefinition = "TEXT") private String description;
    @Enumerated(EnumType.STRING) @Column(name = "room_type", nullable = false, length = 30) private RoomType roomType;
    @Column(nullable = false) private Integer capacity;
    @Column(name = "bed_count", nullable = false) private Integer bedCount;
    @Column(name = "bed_type", length = 100) private String bedType;
    @Column(name = "price_per_night", nullable = false, precision = 10, scale = 2) private BigDecimal pricePerNight;
    @Column(name = "total_rooms", nullable = false) private Integer totalRooms;
    @Column(name = "available_rooms", nullable = false) private Integer availableRooms;
    @Column(name = "created_at", nullable = false, updatable = false) private LocalDateTime createdAt;
    @Column(name = "updated_at", nullable = false) private LocalDateTime updatedAt;
}

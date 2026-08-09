package com.example.hotel_service.entity;

import com.example.hotel_service.enums.RoomType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
    name = "rooms",
    indexes = {
        @Index(name = "idx_rooms_hotel_id", columnList = "hotel_id"),
        @Index(name = "idx_rooms_type", columnList = "room_type")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "hotel_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_room_hotel")
    )
    private Hotel hotel;

    @Column(name = "room_number", nullable = false, length = 50)
    private String roomNumber;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "room_type", nullable = false, length = 30)
    private RoomType roomType;

    @Column(nullable = false)
    private Integer capacity;

    @Column(name = "bed_count", nullable = false)
    private Integer bedCount;

    @Column(name = "bed_type", length = 100)
    private String bedType;

    @Column(
        name = "price_per_night",
        nullable = false,
        precision = 10,
        scale = 2
    )
    private BigDecimal pricePerNight;

    @Column(name = "total_rooms", nullable = false)
    private Integer totalRooms;

    @Column(name = "available_rooms", nullable = false)
    private Integer availableRooms;

    @OneToMany(
        mappedBy = "room",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @Builder.Default
    private List<RoomImage> images = new ArrayList<>();

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
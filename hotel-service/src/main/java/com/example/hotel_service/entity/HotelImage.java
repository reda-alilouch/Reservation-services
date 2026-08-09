package com.example.hotel_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
    name = "hotel_images",
    indexes = {
        @Index(name = "idx_hotel_images_hotel_id", columnList = "hotel_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HotelImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "hotel_id",
        nullable = false,
        foreignKey = @ForeignKey(name = "fk_hotel_image_hotel")
    )
    private Hotel hotel;

    @Column(name = "image_url", nullable = false, length = 500)
    private String imageUrl;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "is_main", nullable = false)
    private Boolean main = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
package com.example.hotel_service.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "room_images", indexes = @Index(name = "idx_room_images_room_id", columnList = "room_id"))
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RoomImage {
    @Id @GeneratedValue(strategy = GenerationType.UUID) private UUID id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) @JoinColumn(name = "room_id", nullable = false, foreignKey = @ForeignKey(name = "fk_room_image_room")) private Room room;
    @Column(name = "image_url", nullable = false, length = 500) private String imageUrl;
    @Column(name = "display_order") private Integer displayOrder;
    @Column(name = "is_main", nullable = false) private Boolean main = false;
    @Column(name = "created_at", nullable = false, updatable = false) private LocalDateTime createdAt;
    @PrePersist protected void onCreate() { createdAt = LocalDateTime.now(); }
}

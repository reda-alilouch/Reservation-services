package com.example.hotel_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name="hotel_images")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HotelImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name="hotel_id")
    private Hotel hotel;
}

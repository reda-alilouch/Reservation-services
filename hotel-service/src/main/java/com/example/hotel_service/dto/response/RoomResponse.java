package com.example.hotel_service.dto.response;

import com.example.hotel_service.enums.RoomType;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@Builder
public class RoomResponse {

    private UUID id;

    private RoomType type;

    private Integer capacity;

    private BigDecimal pricePerNight;
}

package com.example.hotel_service.dto.request;

import com.example.hotel_service.enums.RoomType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
public class CreateRoomRequest {

    private RoomType type;

    private Integer capacity;

    private BigDecimal pricePerNight;

    private Integer availableCount;
}

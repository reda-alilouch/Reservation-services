package com.example.hotel_service.service.impl;


import com.example.hotel_service.dto.request.CreateHotelRequest;
import com.example.hotel_service.dto.request.RejectHotelRequest;
import com.example.hotel_service.dto.response.HotelDetailsResponse;
import com.example.hotel_service.dto.response.HotelResponse;
import com.example.hotel_service.entity.Hotel;
import com.example.hotel_service.enums.HotelStatus;
import com.example.hotel_service.mapper.HotelMapper;
import com.example.hotel_service.repository.HotelRepository;
import com.example.hotel_service.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;

    @Override
    public HotelResponse createHotel(
            CreateHotelRequest request
    ) {
        Hotel hotel = Hotel.builder()
                .name(request.getName())
                .description(request.getDescription())
                .city(request.getCity())
                .address(request.getAddress())
                .status(HotelStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        hotelRepository.save(hotel);

        return HotelMapper.toResponse(hotel);
    }
}
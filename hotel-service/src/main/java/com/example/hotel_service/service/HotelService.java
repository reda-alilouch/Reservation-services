package com.example.hotel_service.service;

import com.example.hotel_service.dto.request.CreateHotelRequest;
import com.example.hotel_service.dto.request.RejectHotelRequest;
import com.example.hotel_service.dto.response.HotelDetailsResponse;
import com.example.hotel_service.dto.response.HotelResponse;

import java.util.List;
import java.util.UUID;

public interface HotelService {

    HotelResponse createHotel(CreateHotelRequest request);

    HotelDetailsResponse getHotel(UUID id);

    List<HotelResponse> getApprovedHotels();

    HotelResponse approveHotel(UUID id);

    HotelResponse rejectHotel(
            UUID id,
            RejectHotelRequest request
    );
}

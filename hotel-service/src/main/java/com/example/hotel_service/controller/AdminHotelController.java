package com.example.hotel_service.controller;

import com.example.hotel_service.dto.request.RejectHotelRequest;
import com.example.hotel_service.service.HotelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class AdminHotelController {

    private final HotelService hotelService;

    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approve(
            @PathVariable UUID id
    ){

        return ResponseEntity.ok(
                hotelService.approveHotel(id)
        );
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> reject(
            @PathVariable UUID id,
            @RequestBody RejectHotelRequest request
    ){

        return ResponseEntity.ok(
                hotelService.rejectHotel(id,request)
        );
    }
}

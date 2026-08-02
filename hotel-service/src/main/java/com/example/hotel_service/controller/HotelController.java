package com.example.hotel_service.controller;

import com.example.hotel_service.dto.request.CreateHotelRequest;
import com.example.hotel_service.service.HotelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {
    private final HotelService hotelService;

    @PostMapping
    public ResponseEntity<?> create(
            @Valid
            @RequestBody CreateHotelRequest request
            ){
        return ResponseEntity.ok(
                hotelService.createHotel(request)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getHotel(
            @PathVariable UUID id
    ){

        return ResponseEntity.ok(
                hotelService.getHotel(id)
        );
    }
}

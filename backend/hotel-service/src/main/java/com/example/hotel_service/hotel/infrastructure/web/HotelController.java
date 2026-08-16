package com.example.hotel_service.hotel.infrastructure.web;

import com.example.hotel_service.hotel.application.dto.request.*;
import com.example.hotel_service.hotel.application.dto.response.HotelResponse;
import com.example.hotel_service.hotel.application.port.in.HotelUseCase;
import jakarta.validation.Valid;
import java.util.*;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/hotel")
public class HotelController {
  private final HotelUseCase service;

  public HotelController(HotelUseCase service) {
    this.service = service;
  }

  @GetMapping
  public List<HotelResponse> all() {
    return service.findAll();
  }

  @GetMapping("/{id}")
  public HotelResponse one(@PathVariable UUID id) {
    return service.findById(id);
  }

  @PostMapping
  @PreAuthorize("hasRole('PRESTATAIRE')")
  public ResponseEntity<HotelResponse> create(@Valid @RequestBody CreateHotelRequest r) {
    return ResponseEntity.status(HttpStatus.CREATED).body(service.create(r));
  }

  @PutMapping("/{id}")
  @PreAuthorize("hasRole('PRESTATAIRE')")
  public HotelResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateHotelRequest r) {
    return service.update(id, r);
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('PRESTATAIRE')")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void archive(@PathVariable UUID id) {
    service.archive(id);
  }
}

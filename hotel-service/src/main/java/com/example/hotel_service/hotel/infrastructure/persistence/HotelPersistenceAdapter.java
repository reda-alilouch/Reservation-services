package com.example.hotel_service.hotel.infrastructure.persistence;

import com.example.hotel_service.entity.Hotel;
import com.example.hotel_service.hotel.application.port.out.HotelPersistencePort;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class HotelPersistenceAdapter implements HotelPersistencePort {
    private final JpaHotelRepository repository;
    public HotelPersistenceAdapter(JpaHotelRepository repository) { this.repository = repository; }
    public List<Hotel> findAll() { return repository.findAll(); }
    public Optional<Hotel> findById(UUID id) { return repository.findById(id); }
    public Hotel save(Hotel hotel) { return repository.save(hotel); }
    public void delete(Hotel hotel) { repository.delete(hotel); }
}

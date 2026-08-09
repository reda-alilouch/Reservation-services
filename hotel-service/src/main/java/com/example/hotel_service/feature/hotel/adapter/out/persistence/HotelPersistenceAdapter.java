package com.example.hotel_service.feature.hotel.adapter.out.persistence;

import com.example.hotel_service.feature.hotel.application.port.out.HotelRepositoryPort;
import com.example.hotel_service.feature.hotel.domain.model.Hotel;
import com.example.hotel_service.feature.hotel.domain.model.HotelStatus;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class HotelPersistenceAdapter implements HotelRepositoryPort {
    private final SpringDataHotelRepository repository;
    public HotelPersistenceAdapter(SpringDataHotelRepository repository) { this.repository = repository; }
    @Override public Hotel save(Hotel hotel) { return toDomain(repository.save(toEntity(hotel))); }
    @Override public Optional<Hotel> findById(UUID id) { return repository.findById(id).map(HotelPersistenceAdapter::toDomain); }
    @Override public List<Hotel> findByNameContainingIgnoreCase(String name) { return repository.findByNameContainingIgnoreCase(name).stream().map(HotelPersistenceAdapter::toDomain).toList(); }
    @Override public List<Hotel> findByStatus(HotelStatus status) { return repository.findByStatus(status).stream().map(HotelPersistenceAdapter::toDomain).toList(); }
    private static HotelJpaEntity toEntity(Hotel hotel) {
        HotelJpaEntity entity = new HotelJpaEntity();
        entity.setId(hotel.id()); entity.setProviderId(hotel.providerId()); entity.setName(hotel.name());
        entity.setDescription(hotel.description()); entity.setAddress(hotel.address()); entity.setCity(hotel.city());
        entity.setCountry(hotel.country()); entity.setStatus(hotel.status()); entity.setCreatedAt(hotel.createdAt()); entity.setUpdatedAt(hotel.updatedAt());
        return entity;
    }
    private static Hotel toDomain(HotelJpaEntity entity) {
        return new Hotel(entity.getId(), entity.getProviderId(), entity.getName(), entity.getDescription(), entity.getAddress(),
                entity.getCity(), entity.getCountry(), entity.getStatus(), entity.getCreatedAt(), entity.getUpdatedAt());
    }
}

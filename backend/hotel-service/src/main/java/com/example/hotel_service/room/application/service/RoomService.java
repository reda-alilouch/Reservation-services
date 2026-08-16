package com.example.hotel_service.room.application.service;

import com.example.hotel_service.entity.*;
import com.example.hotel_service.hotel.application.port.out.HotelPersistencePort;
import com.example.hotel_service.room.application.dto.request.*;
import com.example.hotel_service.room.application.dto.response.RoomResponse;
import com.example.hotel_service.room.application.port.in.RoomUseCase;
import com.example.hotel_service.room.application.port.out.RoomPersistencePort;
import com.example.hotel_service.shared.exception.ResourceNotFoundException;
import java.util.*;

public class RoomService implements RoomUseCase {
  private final RoomPersistencePort rooms;
  private final HotelPersistencePort hotels;

  public RoomService(RoomPersistencePort rooms, HotelPersistencePort hotels) {

    this.rooms = rooms;
    this.hotels = hotels;
  }

  public List<RoomResponse> findAll() {
    return rooms.findAll().stream().map(this::out).toList();
  }

  public RoomResponse findById(UUID id) {
    return out(room(id));
  }

  public RoomResponse create(CreateRoomRequest r) {
    Room x = new Room();
    x.setHotel(hotel(r.hotelId()));
    apply(
        x,
        r.roomNumber(),
        r.name(),
        r.description(),
        r.roomType(),
        r.capacity(),
        r.bedCount(),
        r.bedType(),
        r.pricePerNight(),
        r.totalRooms(),
        r.availableRooms());
    return out(rooms.save(x));
  }

  public RoomResponse update(UUID id, UpdateRoomRequest r) {
    Room x = room(id);
    apply(
        x,
        r.roomNumber(),
        r.name(),
        r.description(),
        r.roomType(),
        r.capacity(),
        r.bedCount(),
        r.bedType(),
        r.pricePerNight(),
        r.totalRooms(),
        r.availableRooms());
    return out(rooms.save(x));
  }

  public void delete(UUID id) {
    rooms.delete(room(id));
  }

  private Room room(UUID id) {
    return rooms
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Room not found: " + id));
  }

  private Hotel hotel(UUID id) {
    return hotels
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Hotel not found: " + id));
  }

  private void apply(
      Room x,
      String no,
      String n,
      String d,
      com.example.hotel_service.enums.RoomType t,
      Integer c,
      Integer b,
      String bt,
      java.math.BigDecimal p,
      Integer total,
      Integer available) {
    x.setRoomNumber(no);
    x.setName(n);
    x.setDescription(d);
    x.setRoomType(t);
    x.setCapacity(c);
    x.setBedCount(b);
    x.setBedType(bt);
    x.setPricePerNight(p);
    x.setTotalRooms(total);
    x.setAvailableRooms(available);
  }

  private RoomResponse out(Room x) {
    return new RoomResponse(
        x.getId(),
        x.getHotel().getId(),
        x.getRoomNumber(),
        x.getName(),
        x.getDescription(),
        x.getRoomType(),
        x.getCapacity(),
        x.getBedCount(),
        x.getBedType(),
        x.getPricePerNight(),
        x.getTotalRooms(),
        x.getAvailableRooms());
  }
}

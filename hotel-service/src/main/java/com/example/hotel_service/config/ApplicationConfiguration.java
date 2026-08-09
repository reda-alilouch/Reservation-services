package com.example.hotel_service.config;

import com.example.hotel_service.hotel.application.port.out.HotelPersistencePort;
import com.example.hotel_service.hotel.application.service.HotelService;
import com.example.hotel_service.room.application.port.out.RoomPersistencePort;
import com.example.hotel_service.room.application.service.RoomService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    HotelService hotelService(HotelPersistencePort hotelPersistence) {
        return new HotelService(hotelPersistence);
    }

    @Bean
    RoomService roomService(RoomPersistencePort roomPersistence, HotelPersistencePort hotelPersistence) {
        return new RoomService(roomPersistence, hotelPersistence);
    }
}

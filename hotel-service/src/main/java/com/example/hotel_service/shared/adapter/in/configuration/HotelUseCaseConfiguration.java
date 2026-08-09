package com.example.hotel_service.shared.adapter.in.configuration;

import com.example.hotel_service.feature.hotel.application.port.out.HotelRepositoryPort;
import com.example.hotel_service.feature.hotel.application.service.HotelApplicationService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Framework wiring lives at the edge; the application service remains plain Java. */
@Configuration
public class HotelUseCaseConfiguration {
    @Bean
    HotelApplicationService hotelApplicationService(HotelRepositoryPort hotelRepository) {
        return new HotelApplicationService(hotelRepository);
    }
}

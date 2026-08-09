package com.example.hotel_service.feature.hotel.adapter.in.web;

import com.example.hotel_service.feature.hotel.application.port.in.CreateHotelUseCase;
import com.example.hotel_service.feature.hotel.application.port.in.GetHotelUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/hotels")
public class HotelController {
    private final CreateHotelUseCase createHotel;
    private final GetHotelUseCase getHotel;
    public HotelController(CreateHotelUseCase createHotel, GetHotelUseCase getHotel) {
        this.createHotel = createHotel; this.getHotel = getHotel;
    }
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    HotelResponse create(@Valid @RequestBody CreateHotelRequest request, Authentication authentication) {
        UUID providerId = currentUser(authentication);
        return HotelResponse.from(createHotel.create(new CreateHotelUseCase.CreateHotelCommand(providerId, request.name(),
                request.description(), request.address(), request.city(), request.country())));
    }
    @GetMapping("/{id}")
    HotelDetailsResponse byId(@PathVariable UUID id) { return HotelDetailsResponse.from(getHotel.getById(id)); }
    @GetMapping
    List<HotelResponse> search(@RequestParam(required = false) String name, @RequestParam(required = false, defaultValue = "false") boolean approved) {
        return (approved ? getHotel.getApproved() : getHotel.getByName(name == null ? "" : name)).stream().map(HotelResponse::from).toList();
    }
    private static UUID currentUser(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UUID userId)) {
            throw new UnauthorizedException();
        }
        return userId;
    }
}

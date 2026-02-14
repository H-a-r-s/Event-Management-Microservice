package com.harsh.registration_service.service;
import com.harsh.registration_service.dto.RegistrationCreateRequest;
import com.harsh.registration_service.dto.RegistrationResponse;
import com.harsh.registration_service.entity.Registration;
import com.harsh.registration_service.entity.RegistrationStatus;
import com.harsh.registration_service.exception.InvalidEventException;
import com.harsh.registration_service.exception.InvalidUserException;
import com.harsh.registration_service.exception.RegistrationNotFoundException;
import com.harsh.registration_service.repository.RegistrationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final RegistrationRepository repo;
    private final RestTemplate restTemplate;

    // TEMP: direct URL (later Gateway/Eureka)
    // TEMP: direct URL (later Gateway/Eureka)
    private static final String EVENT_SERVICE_URL = "http://localhost:8082/api/events/";
    private static final String USER_SERVICE_URL  = "http://localhost:8081/api/users/";

    public RegistrationResponse create(RegistrationCreateRequest req) {

        System.out.println("Calling USER service: " + USER_SERVICE_URL + req.getUserId());
        System.out.println("Calling EVENT service: " + EVENT_SERVICE_URL + req.getEventId());

        try {
            restTemplate.getForObject(USER_SERVICE_URL + req.getUserId(), Object.class);
        } catch (Exception e) {
            throw new InvalidUserException("User not found with id " + req.getUserId());

        }
        try {
            restTemplate.getForObject(EVENT_SERVICE_URL + req.getEventId(), Object.class);
        } catch (Exception e) {
            throw new InvalidEventException("Event not found with id " + req.getEventId());
        }


        Registration reg = Registration.builder()
                .userId(req.getUserId())
                .eventId(req.getEventId())
                .status(RegistrationStatus.PENDING)
                .ticketNumber(null)
                .registeredAt(LocalDateTime.now())
                .build();

        Registration saved = repo.save(reg);
        return mapToResponse(saved);
    }

    public List<RegistrationResponse> getAll() {
        return repo.findAll().stream().map(this::mapToResponse).toList();
    }

    public RegistrationResponse getById(Long id) {
        Registration reg = repo.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException("Registration not found with id " + id));
        return mapToResponse(reg);
    }

    public List<RegistrationResponse> getByUserId(Long userId) {
        return repo.findByUserId(userId).stream().map(this::mapToResponse).toList();
    }

    public RegistrationResponse cancel(Long id) {
        Registration reg = repo.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException("Registration not found with id " + id));
        reg.setStatus(RegistrationStatus.CANCELLED);
        reg.setTicketNumber(null);
        return mapToResponse(repo.save(reg));
    }

    // Optional endpoint for now (later Payment success will call it)
    public RegistrationResponse confirm(Long id) {
        Registration reg = repo.findById(id)
                .orElseThrow(() -> new RegistrationNotFoundException("Registration not found with id " + id));

        reg.setStatus(RegistrationStatus.CONFIRMED);
        if (reg.getTicketNumber() == null) {
            reg.setTicketNumber("TKT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        return mapToResponse(repo.save(reg));
    }

    private RegistrationResponse mapToResponse(Registration reg) {
        return RegistrationResponse.builder()
                .registrationId(reg.getRegistrationId())
                .userId(reg.getUserId())
                .eventId(reg.getEventId())
                .ticketNumber(reg.getTicketNumber())
                .status(reg.getStatus())
                .registeredAt(reg.getRegisteredAt())
                .build();
    }
}
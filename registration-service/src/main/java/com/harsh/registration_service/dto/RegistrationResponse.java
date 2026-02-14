package com.harsh.registration_service.dto;

import com.harsh.registration_service.entity.RegistrationStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationResponse {

    private Long registrationId;
    private Long userId;
    private Long eventId;
    private String ticketNumber;
    private RegistrationStatus status;
    private LocalDateTime registeredAt;
}

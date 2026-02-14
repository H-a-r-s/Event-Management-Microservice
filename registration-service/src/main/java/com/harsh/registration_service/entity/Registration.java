package com.harsh.registration_service.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registrations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long registrationId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long eventId;

    @Column(unique = true, nullable = true)
    private String ticketNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status;

    @Column(nullable = false)
    private LocalDateTime registeredAt;
}

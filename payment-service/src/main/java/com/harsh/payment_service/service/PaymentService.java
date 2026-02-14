package com.harsh.payment_service.service;
import com.harsh.payment_service.dto.PaymentRequestDto;
import com.harsh.payment_service.dto.PaymentResponseDto;
import com.harsh.payment_service.entity.Payment;
import com.harsh.payment_service.entity.PaymentStatus;
import com.harsh.payment_service.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repo;
    private final RestTemplate restTemplate;

    // Registration confirm endpoint
    private static final String REG_CONFIRM_URL = "http://localhost:8084/api/register/";
    private static final String EVENT_SEAT_URL   = "http://localhost:8082/api/events/";

    public PaymentResponseDto pay(PaymentRequestDto req) {

        PaymentStatus status = Boolean.TRUE.equals(req.getSuccess())
                ? PaymentStatus.SUCCESS
                : PaymentStatus.FAILED;

        Payment payment = Payment.builder()
                .registrationId(req.getRegistrationId())
                .amount(req.getAmount())
                .status(status)
                .paymentDate(LocalDateTime.now())
                .build();

        Payment saved = repo.save(payment);

        // If SUCCESS -> confirm registration
        if (status == PaymentStatus.SUCCESS) {
            restTemplate.put(REG_CONFIRM_URL + req.getRegistrationId() + "/confirm", null);

            restTemplate.put(EVENT_SEAT_URL + req.getEventId() + "/decrease-seat", null);
        }

        return mapToResponse(saved);
    }

    private PaymentResponseDto mapToResponse(Payment p) {
        return PaymentResponseDto.builder()
                .paymentId(p.getPaymentId())
                .registrationId(p.getRegistrationId())
                .amount(p.getAmount())
                .status(p.getStatus())
                .paymentDate(p.getPaymentDate())
                .build();
    }
}

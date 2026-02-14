package com.harsh.payment_service.dto;

import com.harsh.payment_service.entity.PaymentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDto {

    private Long paymentId;
    private Long registrationId;
    private Double amount;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
}

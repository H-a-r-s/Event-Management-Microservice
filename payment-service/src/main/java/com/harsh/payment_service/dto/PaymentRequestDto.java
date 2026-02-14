package com.harsh.payment_service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentRequestDto {

    @NotNull
    private Long registrationId;

    @NotNull
    private Double amount;

    @NotNull
    private Long eventId;


    // For mock: true -> SUCCESS, false -> FAILED
    @NotNull
    private Boolean success;
}

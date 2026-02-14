package com.harsh.payment_service.controller;

import com.harsh.payment_service.dto.PaymentRequestDto;
import com.harsh.payment_service.dto.PaymentResponseDto;
import com.harsh.payment_service.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService service;

    @PostMapping("/pay")
    public PaymentResponseDto pay(@Valid @RequestBody PaymentRequestDto req) {
        return service.pay(req);
    }
}
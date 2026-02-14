package com.harsh.payment_service.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "event-service")
public interface EventClient {

    @PutMapping("/api/events/{id}/decrease-seat")
    void decreaseSeat(@PathVariable Long id);
}
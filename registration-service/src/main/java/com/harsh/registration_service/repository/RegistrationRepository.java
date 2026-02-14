package com.harsh.registration_service.repository;

import com.harsh.registration_service.entity.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    List<Registration> findByUserId(Long userId);
}

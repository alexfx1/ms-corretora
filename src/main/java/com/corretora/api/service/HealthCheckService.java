package com.corretora.api.service;

import com.corretora.api.repository.MercadoriaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HealthCheckService implements HealthIndicator {

    @Autowired
    private MercadoriaRepository mercadoriaRepository;


    @Override
    public Health health() {
        try {
            if (mercadoriaRepository.count() >= 0) {
                return Health.up().withDetail("database", "Available").build();
            }
        } catch (Exception e) {
            log.error("Err in health method: " + e.getMessage());
            return Health.down().withDetail("database", "Not Available").withException(e).build();
        }
        return Health.unknown().build();
    }

}

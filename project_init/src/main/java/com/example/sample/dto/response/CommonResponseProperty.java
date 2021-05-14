package com.example.sample.dto.response;

import lombok.Data;
import org.slf4j.MDC;

import java.time.Instant;

@Data
public abstract class CommonResponseProperty {
    private final String requestId = MDC.get("requestId");
    private final Long responseTime = Instant.now().toEpochMilli() / 1000;
}

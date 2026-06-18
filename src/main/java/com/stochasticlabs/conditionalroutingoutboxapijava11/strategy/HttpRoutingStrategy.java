package com.stochasticlabs.conditionalroutingoutboxapijava11.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stochasticlabs.conditionalroutingoutboxapijava11.entity.Input;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpRoutingStrategy implements RoutingStrategy {

    private final ObjectMapper objectMapper;

    private final HttpClient httpClient;

    @Value("${app.api.url}")
    private String url;

    @Override
    public boolean validate(Input input) {
        return input.useHttpStrategy();
    }

    @Override
    @CircuitBreaker(name = "java17HttpStrategy", fallbackMethod = "fallbackHttpCall")
    public void execute(Input input) throws JsonProcessingException {
        log.info("[http-routing-strategy-execute] Send [" + input.getInteger() + "] to API.");

        String jsonPayload = objectMapper.writeValueAsString(input);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url+"/api/v1/idempotent/input"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .timeout(Duration.ofSeconds(3))
                .build();

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 201 || response.statusCode() == 200) {
                log.info("[http-routing-strategy-execute] Success: Payload. Status: {}", response.statusCode());
            } else {
                log.warn("[http-routing-strategy-execute] Fail HTTP: API {}. error Circuit Breaker.", response.statusCode());
                throw new RuntimeException("Fail");
            }
        } catch (Exception e) {
            log.error("[http-routing-strategy-execute] Error: API Java 17.", e);
            throw new RuntimeException(e);
        }
    }

    public void fallbackHttpCall(Input input, Throwable t) {
        log.error("[http-routing-strategy-fallback-http-call] [CIRCUIT BREAKER active] Payload [{}]. Error: {}",
                input.getInteger(), t.getMessage());
    }
}

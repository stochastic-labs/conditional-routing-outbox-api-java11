package com.stochasticlabs.conditionalroutingoutboxapijava11.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stochasticlabs.conditionalroutingoutboxapijava11.domain.OutboxStatus;
import com.stochasticlabs.conditionalroutingoutboxapijava11.entity.Input;
import com.stochasticlabs.conditionalroutingoutboxapijava11.entity.Outbox;
import com.stochasticlabs.conditionalroutingoutboxapijava11.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class DatabaseOutboxRoutingStrategy implements RoutingStrategy {

    private final OutboxRepository outboxRepository;

    private final ObjectMapper objectMapper;

    @Override
    public boolean validate(Input input) {
        return input.getInteger() % 2 != 0;
    }

    @Override
    public void execute(Input input) throws JsonProcessingException {
        log.info("Send [" + input.getInteger() + "] to DB.");
        Outbox outbox = Outbox.builder()
                .topic("stochastic-input")
                .payload(objectMapper.writeValueAsString(input))
                .status(OutboxStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        outboxRepository.save(outbox);
    }
}

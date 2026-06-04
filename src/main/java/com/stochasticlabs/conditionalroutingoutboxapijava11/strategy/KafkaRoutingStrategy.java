package com.stochasticlabs.conditionalroutingoutboxapijava11.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stochasticlabs.conditionalroutingoutboxapijava11.entity.Input;
import com.stochasticlabs.conditionalroutingoutboxapijava11.service.KafkaProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaRoutingStrategy implements RoutingStrategy {

    private final KafkaProducerService kafkaProducerService;

    private final ObjectMapper objectMapper;

    @Override
    public boolean validate(Input input) {
        return input.getInteger() % 2 == 0;
    }

    @Override
    public void execute(Input input) throws JsonProcessingException {
        log.info("kafka-routing-strategy-execute: Send [" + input.getInteger() + "] to KAFKA.");
        kafkaProducerService.sendMessage("stochastic-input", objectMapper.writeValueAsString(input));
    }
}

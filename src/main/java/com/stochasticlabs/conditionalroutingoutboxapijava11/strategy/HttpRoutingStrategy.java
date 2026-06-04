package com.stochasticlabs.conditionalroutingoutboxapijava11.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stochasticlabs.conditionalroutingoutboxapijava11.entity.Input;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpRoutingStrategy implements RoutingStrategy {

    @Override
    public boolean validate(Input input) {
        return input.getInteger() % 5 == 0;
    }

    @Override
    public void execute(Input input) throws JsonProcessingException {
        log.info("Send [" + input.getInteger() + "] to API.");
    }
}

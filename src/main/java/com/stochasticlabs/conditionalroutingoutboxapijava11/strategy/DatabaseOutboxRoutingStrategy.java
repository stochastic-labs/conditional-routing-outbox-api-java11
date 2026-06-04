package com.stochasticlabs.conditionalroutingoutboxapijava11.strategy;

import com.stochasticlabs.conditionalroutingoutboxapijava11.dto.InputDTO;
import com.stochasticlabs.conditionalroutingoutboxapijava11.entity.Input;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DatabaseOutboxRoutingStrategy implements RoutingStrategy {

    @Override
    public boolean validate(Input input) {
        return input.getInteger() % 2 != 0;
    }

    @Override
    public void execute(Input input) {
        log.info("Send [" + input.getInteger() + "] to DB.");
    }
}

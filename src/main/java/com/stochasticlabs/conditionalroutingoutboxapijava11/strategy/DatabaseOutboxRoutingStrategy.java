package com.stochasticlabs.conditionalroutingoutboxapijava11.strategy;

import org.springframework.stereotype.Component;

@Component
public class DatabaseOutboxRoutingStrategy implements RoutingStrategy {

    @Override
    public boolean isEligible(Integer number) {
        return number % 2 != 0;
    }

    @Override
    public void route(Integer number) {
        System.out.println("Send [" + number + "] to DB.");
        // TODO: repository.save(new OutboxPayload(number, "PENDING"))
    }
}

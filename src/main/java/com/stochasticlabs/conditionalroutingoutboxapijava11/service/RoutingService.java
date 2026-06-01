package com.stochasticlabs.conditionalroutingoutboxapijava11.service;

import com.stochasticlabs.conditionalroutingoutboxapijava11.strategy.RoutingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoutingService {

    private final List<RoutingStrategy> strategies;

    public RoutingService(List<RoutingStrategy> strategies) {
        this.strategies = strategies;
    }

    public void process(Integer number) {
        List<RoutingStrategy> activeStrategies = strategies.stream()
                .filter(strategy -> strategy.isEligible(number))
                .collect(Collectors.toList());
    }
}

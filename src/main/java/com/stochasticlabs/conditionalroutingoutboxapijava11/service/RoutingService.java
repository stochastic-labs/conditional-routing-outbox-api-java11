package com.stochasticlabs.conditionalroutingoutboxapijava11.service;

import com.stochasticlabs.conditionalroutingoutboxapijava11.dto.InputDTO;
import com.stochasticlabs.conditionalroutingoutboxapijava11.entity.Input;
import com.stochasticlabs.conditionalroutingoutboxapijava11.factory.InputFactory;
import com.stochasticlabs.conditionalroutingoutboxapijava11.strategy.RoutingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoutingService {

    private final List<RoutingStrategy> strategies;

    public RoutingService(List<RoutingStrategy> strategies) {
        this.strategies = strategies;
    }

    public void process(InputDTO inputDTO) {
        Input input = InputFactory.create(inputDTO);
        List<RoutingStrategy> activeStrategies = strategies.stream()
                .filter(strategy -> strategy.validate(input))
                .collect(Collectors.toList());

        if (activeStrategies.isEmpty()) {
            return;
        }

        ExecutorService executor = Executors.newFixedThreadPool(Math.min(activeStrategies.size(), 10));

        try {
            activeStrategies.forEach(strategy -> {
                executor.submit(() -> {
                    try {
                        strategy.execute(input);
                    } catch (Exception e) {
                        log.error("[routing-service-process] Error strategy: " + e.getMessage());
                    }
                });
            });
        } finally {
            executor.shutdown();
        }
    }
}

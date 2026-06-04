package com.stochasticlabs.conditionalroutingoutboxapijava11.job;

import com.stochasticlabs.conditionalroutingoutboxapijava11.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OutboxQueueJob {

    private final OutboxService outboxService;

    @Scheduled(fixedDelay = 5000)
    public void execute() {
        outboxService.execute();
    }
}

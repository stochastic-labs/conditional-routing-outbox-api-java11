package com.stochasticlabs.conditionalroutingoutboxapijava11.job;

import com.stochasticlabs.conditionalroutingoutboxapijava11.service.OutboxService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(value = "app.outbox.enabled", havingValue = "true", matchIfMissing = false)
public class OutboxQueueJob {

    private final OutboxService outboxService;

    @Scheduled(fixedDelayString = "${app.outbox.interval:5000}")
    public void execute() {
        outboxService.execute();
    }
}

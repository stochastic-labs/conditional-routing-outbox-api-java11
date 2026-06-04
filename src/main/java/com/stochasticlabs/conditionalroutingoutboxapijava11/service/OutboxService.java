package com.stochasticlabs.conditionalroutingoutboxapijava11.service;

import com.stochasticlabs.conditionalroutingoutboxapijava11.domain.OutboxStatus;
import com.stochasticlabs.conditionalroutingoutboxapijava11.entity.Outbox;
import com.stochasticlabs.conditionalroutingoutboxapijava11.repository.OutboxRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboxService {

    private final OutboxRepository outboxRepository;

    private final KafkaProducerService kafkaProducerService;

    @Transactional
    public void execute() {
        log.info("outbox-service-execute: Start sweep table outbox...");

        List<Outbox> events = outboxRepository.findTop10ByStatusOrderByIdAsc(OutboxStatus.PENDING);

        if (events.isEmpty()) {
            log.info("outbox-service-execute: Event list empty.");
            return;
        }

        log.info("outbox-service-execute: Find {} events.", events.size());

        for (Outbox event : events) {
            try {
                event.setStatus(OutboxStatus.PROCESSING);
                outboxRepository.saveAndFlush(event);

                kafkaProducerService.sendMessage(event.getTopic(), event.getPayload());

                event.setStatus(OutboxStatus.COMPLETED);
                outboxRepository.save(event);

            } catch (Exception e) {
                log.error("outbox-service-execute-error: Error outbox ID: " + event.getId(), e);
                event.setStatus(OutboxStatus.FAILED);
                outboxRepository.save(event);
            }
        }
    }
}

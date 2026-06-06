package com.stochasticlabs.conditionalroutingoutboxapijava11.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(String topic, String payload) {
        log.info("[kafka-producer-service-send-message] Event send to topic: {} {}", topic, payload);

        kafkaTemplate.send(topic, payload)
                .addCallback(
                        success -> log.info("[kafka-producer-service-send-message] Success! Offset: {}", success.getRecordMetadata().offset()),
                        failure -> log.error("[kafka-producer-service-send-message] Fail Kafka:", failure.getCause())
                );
    }
}

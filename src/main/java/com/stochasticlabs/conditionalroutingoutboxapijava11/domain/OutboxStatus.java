package com.stochasticlabs.conditionalroutingoutboxapijava11.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum OutboxStatus {

    PENDING("PENDING", "Event awaiting processing by Job"),
    PROCESSING("PROCESSING", "Event captured and in the process of being sent to the Broker"),
    COMPLETED("COMPLETED", "Event successfully sent to Kafka/RabbitMQ"),
    FAILED("FAILED", "Error occurred while attempting to transmit the event");

    private final String value;

    private final String description;

    public static OutboxStatus fromValue(String value) {
        for (OutboxStatus status : OutboxStatus.values()) {
            if (status.getValue().equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown outbox status: " + value);
    }
}

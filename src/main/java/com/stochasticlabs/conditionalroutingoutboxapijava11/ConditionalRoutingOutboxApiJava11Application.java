package com.stochasticlabs.conditionalroutingoutboxapijava11;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ConditionalRoutingOutboxApiJava11Application {
    public static void main(String[] args) {
        SpringApplication.run(ConditionalRoutingOutboxApiJava11Application.class, args);
    }
}

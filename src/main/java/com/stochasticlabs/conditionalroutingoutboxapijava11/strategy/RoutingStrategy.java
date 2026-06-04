package com.stochasticlabs.conditionalroutingoutboxapijava11.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.stochasticlabs.conditionalroutingoutboxapijava11.entity.Input;

public interface RoutingStrategy {
    boolean validate(Input input);
    void execute(Input input) throws JsonProcessingException;
}

package com.stochasticlabs.conditionalroutingoutboxapijava11.strategy;

public interface RoutingStrategy {
    boolean isEligible(Integer number);
    void route(Integer number);
}

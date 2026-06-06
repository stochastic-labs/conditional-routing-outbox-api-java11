package com.stochasticlabs.conditionalroutingoutboxapijava11.entity;

public class Input {

    private final int integer;

    public Input(int integer) {
        this.integer = integer;
    }

    public int getInteger() {
        return integer;
    }

    public boolean useOutboxStrategy() {
        return integer % 2 != 0;
    }

    public boolean useHttpStrategy() {
        return integer % 5 == 0;
    }

    public boolean useKafkaStrategy() {
        return integer % 2 == 0;
    }
}

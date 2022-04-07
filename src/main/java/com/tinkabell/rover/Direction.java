package com.tinkabell.rover;

public enum Direction {
    N (Delta.N),
    S (Delta.S),
    E (Delta.E),
    W (Delta.W);

    private final Delta delta;

    Direction(Delta delta) {
        this.delta = delta;
    }

    public Delta getDelta() {
        return delta;
    }
}

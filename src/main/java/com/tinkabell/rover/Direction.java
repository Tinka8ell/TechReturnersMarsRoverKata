package com.tinkabell.rover;

/**
 * Direction represents the 4 compass points
 */
public enum Direction {
    N (Delta.N),
    S (Delta.S),
    E (Delta.E),
    W (Delta.W);

    private final Delta delta;

    Direction(Delta delta) {
        this.delta = delta;
    }

    /**
     * Get a Delta equivalent to this compass point
     *
     * @return new Delta representing this direction
     */
    public Delta getDelta() {
        return new Delta(delta);
    }
}

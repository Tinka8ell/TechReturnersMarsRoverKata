package com.tinkabell.rover;

public interface Obstacle {

    /**
     * Create a new Obstacle on the destruction of this Obstacle
     *
     * @return a new Obstacle or null
     */
    Obstacle destroy();

    /**
     * Return the value of this Obstacle in mineral units
     *
     * @return int amount of minerals
     */
    int getMinerals();

    /**
     * Can this Obstacle be mined?
     *
     * @return true iff it can be mined
     */
    boolean canBeMined();

    /**
     * Return representation of this object
     *
     * @return a char representing this objective
     */
    String representation();
}

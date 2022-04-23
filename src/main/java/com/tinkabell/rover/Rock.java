package com.tinkabell.rover;

public class Rock implements Obstacle {
    /**
     * Create a new Obstacle on the destruction of this Obstacle
     *
     * @return a new Obstacle or null
     */
    @Override
    public Obstacle destroy() {
        return new Rubble(getMinerals());
    }

    /**
     * Return the value of this Obstacle in mineral units
     *
     * @return int amount of minerals
     */
    @Override
    public int getMinerals() {
        return 1;
    }

    /**
     * Can this Obstacle be mined?
     *
     * @return true iff it can be mined
     */
    @Override
    public boolean canBeMined() {
        return true;
    }

    /**
     * Return representation of this object
     *
     * @return a char representing this objective
     */
    @Override
    public String representation() {
        return "M";
    }
}

package com.tinkabell.rover;

public class Rubble implements Obstacle {
    private int minerals;

    public Rubble(int i) {
        minerals = i;
    }

    public int getMinerals() {
        return minerals;
    }

    /**
     * Rubble can be mined?
     *
     * @return true
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
        return "m";
    }

    /**
     * Destroying Rubble leaves nothing
     *
     * @return null
     */
    @Override
    public Obstacle destroy() {
        return null;
    }
}

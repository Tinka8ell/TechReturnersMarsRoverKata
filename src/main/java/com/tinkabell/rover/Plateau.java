package com.tinkabell.rover;

/**
 * Plateau
 *
 * A representation fo the world.
 * Initially it just has width and height,
 * but will contain everything in the world,
 * where it is and their movement.
 */
public class Plateau {

    private final int width;
    private final int height;

    /**
     * Create and empty world.
     *
     * @param width Integer of the Plateau
     * @param height Integer of the Plateau
     */
    public Plateau(int width, int height) {
        this.width = width;
        this.height = height;
    }

    /**
     * The inspector.
     * A bit like the toString() method, but used for debugging and testing.
     *
     * @return String representing the internals for testing
     */
    public String inspector() {
        return "Plateau{width: " + width + ", height: " + height + "}";
    }
}

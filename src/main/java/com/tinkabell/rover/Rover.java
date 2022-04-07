package com.tinkabell.rover;

/**
 * A rover is a semi-autonomous vehicle
 * that accepts commands and actions them.
 */
public class Rover {

    private final Direction direction;

    /**
     * Create a new rover
     *
     * @param direction it is pointing in
     */
    public Rover(Direction direction) {
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }
}

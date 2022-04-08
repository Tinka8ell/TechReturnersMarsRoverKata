package com.tinkabell.rover;

/**
 * A location or address used in the Plateau
 */
public class Location extends IntVector2D{

    /**
     * Basic constructor
     *
     * @param x coordinate
     * @param y coordinate
     */
    public Location(int x, int y) {
        super(x, y);
    }

    /**
     * Clone a location
     *
     * @param vector to clone
     */
    public Location(IntVector2D vector) {
        super(vector);
    }

    /**
     * Used to represent a location as a String
     * Remove the brackets from the tuple
     *
     * @return a condensed form of the IntVector2D representation
     */
    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(1, string.length() - 1);
    }

    /**
     * Move this location ba a Delta
     *
     * @param delta amount and direction to move
     */
    public void add(Delta delta) {
        super.add(delta);
    }
}

package com.tinkabell.rover;

import java.util.Objects;

/**
 * Integer 2D Vector.
 * This class holds integer values in two directions.
 * It will support vector addition.
 */
public class IntVector2D {

    // these will be mutable values, so are not final
    private int x;
    private int y;

    /**
     * Basic constructor
     *
     * @param x coordinate
     * @param y coordinate
     */
    public IntVector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Clone a vector
     *
     * @param vector to clone
     */
    public IntVector2D(IntVector2D vector) {
        this(vector.x, vector.y);
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    /**
     * Get a view to help with testing.
     *
     * @return view of IntVector2D
     */
    public String inspector() {
        return String.format("%d %d", x, y);
    }

    /**
     * A basic String representation
     *
     * @return IntVector2D as tuple String
     */
    @Override
    public String toString() {
        return "(" + x + " " + y + ")";
    }

    /**
     * Basic equals method.
     * Check data is the same.
     *
     * @param o to compare
     * @return true iff it is "equal" to us
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntVector2D that)) return false;
        return x == that.x && y == that.y;
    }

    /**
     * Convert to hashcode for mapping
     *
     * @return an int hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Rotate this vector through 90 degrees
     * Positive is to the right (clockwise)
     * Negative is to the left (anticlockwise)
     *
     * @param sign direction to rotate
     */
    public void turn(int sign) {
        for (int i = 0; i < Math.abs(sign); i++) {
            switch (Integer.signum(sign)){
                case 1 -> { // turn right
                    int swap = x;
                    //noinspection SuspiciousNameCombination
                    x = y;
                    y = -swap;
                }
                case -1 -> { // turn left
                    int swap = x;
                    x = -y;
                    y = swap;
                }
            }
        }
    }

    /**
     * Add a IntVector2D change to this
     *
     * @param change vector to move this
     */
    public void add(IntVector2D change) {
        x += change.x;
        y += change.y;
    }

    /**
     * Is the IntVector2D inside the box
     * from (x, y) of size (width, height) inclusive
     *
     * @param x coordinate of bottom left corner
     * @param y coordinate of bottom left corner
     * @param width of the box
     * @param height of the box
     *
     * @return true iff inside or on edge of box
     */
    public boolean isValid(int x, int y, int width, int height) {
        return this.x >= x && this.x <= x + width && this.y >= y && this.y <= y + height;
    }
}

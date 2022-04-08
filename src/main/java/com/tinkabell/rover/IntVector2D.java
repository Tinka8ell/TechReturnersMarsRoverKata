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

    public IntVector2D(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public IntVector2D(IntVector2D vector) {
        this(vector.x, vector.y);
    }

    public String inspector() {
        return String.format("%d %d", x, y);
    }

    @Override
    public String toString() {
        return "(" + x + " " + y + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntVector2D that)) return false;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public void turn(int sign) {
        for (int i = 0; i < Math.abs(sign); i++) {
            switch (Integer.signum(sign)){
                case 1 -> { // turn right
                    int swap = x;
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

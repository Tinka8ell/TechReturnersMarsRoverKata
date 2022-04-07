package com.tinkabell.rover;

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

    public String inspector() {
        return String.format("%d %d", x, y);
    }

}

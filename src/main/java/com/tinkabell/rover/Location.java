package com.tinkabell.rover;

public class Location extends IntVector2D{

    public Location(int x, int y) {
        super(x, y);
    }

    public Location(IntVector2D vector) {
        super(vector);
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(1, string.length() - 1);
    }

    public void add(Delta delta) {
        super.add(delta);
    }
}

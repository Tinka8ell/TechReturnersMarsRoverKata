package com.tinkabell.rover;

public class Location extends IntVector2D{

    public Location(int x, int y) {
        super(x, y);
    }

    @Override
    public String toString() {
        String string = super.toString();
        return string.substring(1, string.length() - 1);
    }
}

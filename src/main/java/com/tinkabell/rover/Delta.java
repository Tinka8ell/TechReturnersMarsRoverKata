package com.tinkabell.rover;

public class Delta extends IntVector2D {

    public static final Delta N = new Delta(0, 1);
    public static final Delta S = new Delta(0, -1);
    public static final Delta E = new Delta(1, 0);
    public static final Delta W = new Delta(-1, 0);

    public Delta(int x, int y) {
        super(Integer.signum(x), Integer.signum(y));
    }
}
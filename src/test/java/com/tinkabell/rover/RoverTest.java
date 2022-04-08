package com.tinkabell.rover;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class RoverTest {

    public Plateau plateau;
    public Rover n;
    public Rover s;
    public Rover e;
    public Rover w;
    public String nString = "1 2 " + Direction.N;
    public String sString = "2 1 " + Direction.S;
    public String eString = "3 4 " + Direction.E;
    public String wString = "4 3 " + Direction.W;

    @BeforeEach
    void setUp(){
        plateau = new Plateau("10 10");
        n = plateau.getRover(nString);
        s = plateau.getRover(sString);
        e = plateau.getRover(eString);
        w = plateau.getRover(wString);
    }

    @Test
    void checkGetDirection() {
        assertEquals(Direction.N, n.getDirection());
        assertEquals(Direction.S, s.getDirection());
        assertEquals(Direction.E, e.getDirection());
        assertEquals(Direction.W, w.getDirection());
    }

    @Test
    void checkToString() {
        assertEquals(nString, n.toString());
        assertEquals(sString, s.toString());
        assertEquals(eString, e.toString());
        assertEquals(wString, w.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "L, 1 2 W",
            "LL, 1 2 S",
            "LLL, 1 2 E",
            "LLLL, 1 2 N",
    })
    void checkLeftCommand(String command, String expected) {
        n.command(command);
        assertEquals(expected, n.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "R, 2 1 W",
            "RR, 2 1 N",
            "RRR, 2 1 E",
            "RRRR, 2 1 S",
    })
    void checkRightCommand(String command, String expected) {
        s.command(command);
        assertEquals(expected, s.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "LR, 3 4 E",
            "LLRR, 3 4 E",
            "RLLR, 3 4 E",
            "RLR, 3 4 S",
            "LRL, 3 4 N"
    })
    void checkLeftRightCommand(String command, String expected) {
        e.command(command);
        assertEquals(expected, e.toString());
    }

}
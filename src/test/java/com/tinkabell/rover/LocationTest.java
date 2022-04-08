package com.tinkabell.rover;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class LocationTest {

    @ParameterizedTest
    @CsvSource({
            "10, 15, 10 15",
            "10, -7, 10 -7",
            "-9, 123, -9 123",
            "-321, -19, -321 -19",
            "0, 0, 0 0"
    })
    public void checkToString(int x, int y, String expected){
        Location location = new Location(x, y);
        assertEquals(expected, location.toString());
    }

    @ParameterizedTest
    @CsvSource({
            "10, 15, -1, -15 10",
            "10, -7, -1, 7 10",
            "-9, 123, -1, -123 -9 ",
            "-321, -19, -1, 19 -321",
            "0, 0, -1, 0 0",
            "10, 15, 1, 15 -10",
            "10, -7, 1, -7 -10",
            "-9, 123, 1, 123 9 ",
            "-321, -19, 1, -19 321",
            "0, 0, 1, 0 0",
            "10, 15, 0, 10 15",
            "10, -7, 0, 10 -7",
            "-9, 123, 0, -9 123",
            "-321, -19, 0, -321 -19",
            "0, 0, 0, 0 0"
    })
    public void checkTurn(int x, int y, int sign, String expected ){
        Location location = new Location(x, y);
        location.turn(sign);
        assertEquals(expected, location.toString());
    }

}
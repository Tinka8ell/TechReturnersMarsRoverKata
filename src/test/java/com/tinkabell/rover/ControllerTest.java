package com.tinkabell.rover;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {

    public Controller controller;

    @BeforeEach
    public void setup(){
        controller = new Controller();
    }

    @Test
    void checkCreatePlateau() {
        assertEquals("Controller{mars: null}", controller.inspector());
        controller.createPlateau("  11   9   ");
        assertEquals("Controller{mars: Plateau{width: 11, height: 9}}", controller.inspector());
    }

    @ParameterizedTest
    @CsvSource({
            "3",
            "1 2 3",
            "0 100",
            "100 -3",
            "100 N",
            "100,100"
    })
    void checkCreatePlateauStringError(String input) {
        assertThrows(NumberFormatException.class, () -> controller.createPlateau(input));
    }

    @Test
    void checkCreatePlateauBadString() {
        assertThrows(NumberFormatException.class, () -> controller.createPlateau(null), "with null");
        assertThrows(NumberFormatException.class, () -> controller.createPlateau(""), "with empty");
        assertThrows(NumberFormatException.class, () -> controller.createPlateau("    "), "with blank");
        assertThrows(NumberFormatException.class, () -> controller.createPlateau("  1  "), "with too few arguments");
    }

    @Test
    public void checkGetRover(){
        controller.createPlateau("11 9");
        Rover rover = controller.getRover("1 2 N");
        assertEquals("Controller{mars: Plateau{width: 11, height: 9, rovers: {Rover{1 2 N}}}", controller.inspector());
    }

}
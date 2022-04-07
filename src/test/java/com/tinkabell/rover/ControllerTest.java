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
        controller.createPlateau(13, 11);
        assertEquals("Controller{mars: Plateau{width: 13, height: 11}}", controller.inspector());
    }

    @Test
    void checkCreatePlateauString() {
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

    @ParameterizedTest
    @CsvSource({
            "0, 100",
            "100, -3"
    })
    void checkCreatePlateauError(int width, int height) {
        assertThrows(NumberFormatException.class, () -> controller.createPlateau(width, height));
    }

}
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
        controller.setRover("1 2 N");
        assertEquals("Controller{mars: Plateau{width: 11, height: 9, rovers: {Rover{1 2 N}}}", controller.inspector());
    }

    @Test
    public void checkSendRoverOnlyCommands(){
        controller.createPlateau("11 9");
        controller.setRover("1 2 N");
        assertEquals("1 2 W", controller.command("L"));
        assertEquals("Controller{mars: Plateau{width: 11, height: 9, rovers: {Rover{1 2 W}}}", controller.inspector());
        assertEquals("1 2 E", controller.command("RR"));
        assertEquals("Controller{mars: Plateau{width: 11, height: 9, rovers: {Rover{1 2 E}}}", controller.inspector());
    }

    @Test
    public void checkMoveCommands(){
        controller.createPlateau("11 9");
        controller.setRover("3 1 N");
        assertEquals("3 2 N", controller.command("M"));
        assertEquals("3 4 N", controller.command("MM"));
        assertEquals("3 7 N", controller.command("MMM"));
        assertEquals("Controller{mars: Plateau{width: 11, height: 9, rovers: {Rover{3 7 N}}}", controller.inspector());
    }

    @Test
    public void checkWanderCommands(){
        controller.createPlateau("11 9");
        controller.setRover("3 3 N");
        assertEquals("4 4 S", controller.command("MRMR"));
        assertEquals("6 0 S", controller.command("MMLMMRMM"));
        assertEquals("Controller{mars: Plateau{width: 11, height: 9, rovers: {Rover{6 0 S}}}", controller.inspector());
    }

    @Test
    public void checkCommandsThatCrashWithEdge(){
        controller.createPlateau("6 6");
        controller.setRover("3 3 N");
        String response = controller.command("MMMMMMMMMMM");
        assertTrue(response.startsWith("3 6 N"));
        assertTrue(response.indexOf("Bang!") != 0);
        response = controller.command("LMMMMMMMMMMM");
        assertTrue(response.startsWith("0 6 W"));
        assertTrue(response.indexOf("Bang!") != 0);
        response = controller.command("RRMMMMMMMMMMM");
        assertTrue(response.startsWith("6 6 E"));
        assertTrue(response.indexOf("Bang!") != 0);
        response = controller.command("LLLMMMMMMMMMMM");
        assertTrue(response.startsWith("6 0 S"));
        assertTrue(response.indexOf("Bang!") != 0);
        response = controller.command("RMMMMMMMMMMM");
        assertTrue(response.startsWith("0 0 W"));
        assertTrue(response.indexOf("Bang!") != 0);
    }

    @Test
    public void checkCommandsThatCrashWithAnother() {
        controller.createPlateau("6 6");
        controller.setRover("3 3 N");
        controller.setRover("0 3 E");
        String response = controller.command("MMMMMMMMMMM");
        assertTrue(response.startsWith("2 3 E"));
        assertTrue(response.indexOf("Bang!") != 0);
    }

    @Test
    public void checkViewFromRover(){
        controller.createPlateau("10 10");
        controller.setRover("6 7 N");
        controller.setRover("5 5 N");
        String expected = """
                5 5 N\040
                \\       /
                 \\     /
                  \\  A/
                   \\ /""";
        assertEquals(expected, controller.command("V"));
        controller.setRover("7 9 S");
        expected = """
                7 9 S\040
                \\     V /
                 \\     /
                  \\  V/
                   \\ /""";
        assertEquals(expected, controller.command("V"));
        controller.setRover("3 6 E");
        expected = """
                3 6 E\040
                \\>      /
                 \\ <   /
                  \\  </
                   \\ /""";
        assertEquals(expected, controller.command("V"));
        controller.setRover("0 1 W");
        controller.setRover("3 0 W");
        expected = """
                3 0 W\040
                \\MMMMMMM/
                 \\MM A /
                  \\M  /
                   \\ /""";
        assertEquals(expected, controller.command("V"));
    }

}
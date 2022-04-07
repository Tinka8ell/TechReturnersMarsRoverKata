package com.tinkabell.rover;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        Plateau plateau = controller.createPlateau(13, 11);
        assertEquals("Plateau{width: 13, height: 11}", plateau.inspector());
        assertEquals("Controller{mars: Plateau{width: 13, height: 11}}", controller.inspector());
    }
}
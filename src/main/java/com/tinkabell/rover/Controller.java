package com.tinkabell.rover;

/**
 * The controller class.
 *
 * A controller must create its Plateau before further methods can operate.
 */
public class Controller {

    private Plateau mars;

    /**
     * Create the Plateau object.
     *
     * @param width Integer of the underlying Plateau
     * @param height Integer of the underlying Plateau
     */
    public void createPlateau(int width, int height){
        mars = new Plateau(width, height);
    }

    /**
     * Create the Plateau object from String input
     *
     * @param line String containing width and height separated with a space
     */
    public void createPlateau(String line){
        mars = new Plateau(line);
    }

    /**
     * The inspector.
     * A bit like the toString() method, but used for debugging and testing.
     *
     * @return String representing the internals for testing
     */
    protected String inspector(){
        return "Controller{mars: " + (mars == null ? "null": mars.inspector()) + "}";
    }

}

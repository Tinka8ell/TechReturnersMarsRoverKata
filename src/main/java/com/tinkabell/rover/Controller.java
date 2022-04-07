package com.tinkabell.rover;

/**
 * The controller class.
 *
 * A controller must create its Plateau before further methods can operate.
 */
public class Controller {

    private Plateau mars;

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

    public Rover getRover(String line){
        if (mars == null)
            throw new NullPointerException("Can't get Rover until we have a Plateau!");
        return mars.getRover(line);
    }
}

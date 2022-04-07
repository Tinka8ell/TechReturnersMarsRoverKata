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
     * Currently exposes the Plateau, but may not be valid.
     *
     * @param width Integer of the underlying Plateau
     * @param height Integer of the underlying Plateau
     * @return the embedded Plateau object
     */
    public Plateau createPlateau(int width, int height){
        return mars = new Plateau(width, height);
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

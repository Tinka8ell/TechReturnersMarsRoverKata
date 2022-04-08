package com.tinkabell.rover;

/**
 * The controller class.
 *
 * A controller must create its Plateau before further methods can operate.
 */
public class Controller {

    private Plateau mars = null;
    private Rover currentRover = null;

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

    /**
     * Set the current Rover from a Rover string
     *
     * @param line containing location and direction of a Rover
     */
    public void setRover(String line){
        if (mars == null)
            throw new NullPointerException("Can't get Rover until we have a Plateau!");
        currentRover = mars.getRover(line);
    }

    /**
     * Give commands to the current Rover
     * It will "safely" crash into the edges or other objects
     * and stop just before with an extended message
     *
     * @param commandString of action codes
     * @return String of final location of the Rover (toString)
     */
    public String command(String commandString) {
        if (currentRover == null)
            throw new NumberFormatException("Must select a Rover before sending commands");
        String addition = "";
        try{
            currentRover.command(commandString);
        }
        catch (NumberFormatException e){
            addition = " Bang! - " + e.getMessage();
        }
        return currentRover.toString() + addition;
    }
}

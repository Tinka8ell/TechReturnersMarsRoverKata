package com.tinkabell.rover;

import java.util.HashMap;

/**
 * A rover is a semi-autonomous vehicle
 * that accepts commands and actions them.
 */
public class Rover {

    private final Delta facing;
    private final Plateau world;
    private static final HashMap<Delta, Direction> toDirection = new HashMap<>();
    static {
        for (Direction dir: Direction.values()){
            toDirection.put(dir.getDelta(), dir);
        }
    }

    /**
     * Create a new rover
     *
     * @param direction it is pointing in
     */
    public Rover(Plateau plateau, Direction direction) {
        world = plateau;
        facing = direction.getDelta();
    }

    /**
     * Get the direction we are pointing
     *
     * @return a Direction
     */
    public Direction getDirection() {
        return toDirection.get(facing);
    }

    /**
     * The normal representation of a Rover as a String
     *
     * @return String containing location and direction
     */
    @Override
    public String toString() {
        return world.find(this).toString() + " " + getDirection().toString();
    }

    /**
     * Execute a String of commands
     *
     * @param commandString of command codes
     */
    public String command(String commandString) {
        String response = "";
        try {
            for (char code : commandString.toCharArray()) {
                action(code);
            }
        } catch (Exception e) {
            response = e.getMessage();
        }
        return response;
    }

    /**
     * Execute the action code
     * @param code to action (character)
     */
    private void action(int code) throws Exception {
        switch (code){
            case 'L' -> // turn the Rover to the left
                    facing.turn(-1);
            case 'R' -> // turn the Rover to the right
                    facing.turn(1);
            case 'M' -> { // move the Rover forward in the direction it is facing
                String response = world.moveRover(this, facing);
                if (!response.isBlank())
                    throw new Exception(response);
            }
            case 'C' -> // camera look at next grid space
                    throw new Exception(world.viewFromRover(this, facing));
            case 'H', '?' -> // help
                    throw new Exception(help());
            default -> throw new Exception("Error: '" + (char) code + "' is not a recognised action code");
        }
    }

    /**
     * Return description of this Rover's commands
     *
     * @return commands and short description
     */
    public String help() {
        return "Rover commands:\n" +
                "   L - turn left 90 degrees" +
                "   R - turn right 90 degrees" +
                "   M - move forward one grid space" +
                "   C - camera view forward one grid space" +
                "   H or ? - this message";
    }
}

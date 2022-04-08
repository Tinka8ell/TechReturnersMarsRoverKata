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
    public void command(String commandString) {
        commandString
                .chars()
                .forEach(this::action);
    }

    /**
     * Execute the action code
     * @param code to action (character)
     */
    private void action(int code) {
        switch (code){
            case 'L' -> // turn the Rover to the left
                    facing.turn(-1);
            case 'R' -> // turn the Rover to the right
                    facing.turn(1);
            case 'M' -> // move the Rover forward in the direction it is facing
                    world.moveRover(this, facing);
            default -> throw new NumberFormatException("'" + code + "' is not a recognised action code");
        }
    }

}

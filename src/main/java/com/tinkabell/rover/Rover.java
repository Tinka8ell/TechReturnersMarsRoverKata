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

    public Direction getDirection() {
        return toDirection.get(facing);
    }

    @Override
    public String toString() {
        return world.find(this).toString() + " " + facing.toString();
    }

    /**
     * Execute a list of commands
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
            case 'L' -> turnLeft();
            case 'R' -> turnRight();
            default -> throw new NumberFormatException("'" + code + "' is not a recognised action code");
        }
    }

    private void turnLeft() {
        turn(-1);
    }

    private void turnRight() {
        turn(1);
    }

    private void turn(int sign) {
        facing.turn(sign);
    }
}

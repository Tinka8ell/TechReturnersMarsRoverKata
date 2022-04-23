package com.tinkabell.rover;

import java.util.HashMap;

/**
 * A rover is a semi-autonomous vehicle
 * that accepts commands and actions them.
 */
public class Rover implements Obstacle{

    private final Delta facing;
    private final Plateau world;
    private int minerals = 0;
    private int turnsToRecharge = 0;

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
        if(turnsToRecharge > 0)
            turnsToRecharge--;
        return response;
    }

    /**
     * Receive minerals
     *
     * @param amount to add to Rover
     */
    public void receiveMinerals(int amount){
        minerals += amount;
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
            case 'F' -> { // fire the laser
                if (turnsToRecharge > 0)
                    throw new Exception("Laser is still charging so Can't fire for " + turnsToRecharge + " turns");
                else
                    throw new Exception(world.destroy(this, facing));
            }
            case 'D' -> // dig with mining tool
                    throw new Exception(world.dig(this, facing));
            case 'C' -> // camera look at next grid space
                    throw new Exception(world.viewFromRover(this, facing));
            case 'V' -> // view from rover to depth of 3 ...
                    throw new Exception(world.roverView(this, facing, Plateau.MAX_DEPTH)); // depth of 3
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
                "   F - fire mining laser" +
                "   D - dig or mine rubble" +
                "   C - camera view forward one grid space" +
                "   H or ? - this message";
    }

    /**
     * Create a new Rubble Obstacle on the destruction of this Rover
     * It's value (in minerals) is what we were carrying plus 2 for this Rover
     *
     * @return a new Rubble
     */
    @Override
    public Obstacle destroy() {
        return new Rubble(getMinerals());
    }

    /**
     * Return the value of this Rover in mineral units
     *
     * @return int amount of minerals
     */
    @Override
    public int getMinerals() {
        return minerals + 2; // as the Rover is worth 2 on its own
    }

    /**
     * A Rover cannot be mined, it must be destroyed first!
     *
     * @return false
     */
    @Override
    public boolean canBeMined() {
        return false;
    }

    /**
     * Return representation of this object
     *
     * @return a char representing this objective
     */
    @Override
    public String representation() {
        return "R"; // will not be used as rovers have direction!
    }
}

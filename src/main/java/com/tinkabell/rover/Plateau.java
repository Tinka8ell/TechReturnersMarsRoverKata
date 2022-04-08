package com.tinkabell.rover;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Plateau
 *
 * A representation fo the world.
 * Initially it just has width and height,
 * but will contain everything in the world,
 * where it is and their movement.
 */
public class Plateau {

    private int width;
    private int height;
    private Map<Location, Rover> rovers;

    /**
     * Create the Plateau object from String input
     *
     * @param line String containing width and height separated with a space
     */
    public Plateau(String line) {
        if (line == null || line.isBlank())
            throw new NumberFormatException("Must supply width and height");
        String[] parts = Arrays.stream(line.trim().split(" "))
                .filter(Predicate.not(String::isBlank))
                .toList().toArray(new String[0]);
        if (parts.length < 2)
            throw new NumberFormatException("Must supply both width and height");
        if (parts.length > 2)
            throw new NumberFormatException("Should only supply width and height");
        int width = Integer.parseInt(parts[0]);
        int height = Integer.parseInt(parts[1]);
        setDimension(width, height);
    }

    /**
     * Set up an empty world.
     *
     * @param width Integer of the Plateau
     * @param height Integer of the Plateau
     */
    private void setDimension(int width, int height) {
        if (width <= 0) // does not make much sense to have one or less for width
            throw new NumberFormatException("Width must be a natural number");
        if (height <= 0) // does not make much sense to have one or less for height
            throw new NumberFormatException("Height must be a natural number");
        this.width = width;
        this.height = height;
        rovers = new HashMap<>();
    }

    /**
     * The inspector.
     * A bit like the toString() method, but used for debugging and testing.
     *
     * @return String representing the internals for testing
     */
    public String inspector() {
        StringBuilder response = new StringBuilder("Plateau{width: " + width + ", height: " + height);
        if (rovers != null && !rovers.isEmpty()) {
            response.append(", rovers: {");
            for (Location key: rovers.keySet()) {
                Direction direction = rovers.get(key).getDirection();
                response.append("Rover{")
                        .append(key.inspector())
                        .append(" ")
                        .append(direction.toString())
                        .append("}, ");
            }
            response.delete(response.length() - 2, response.length()); // trim extra ", "
        }
        response.append("}");
        return response.toString();
    }

    public Rover getRover(String line) {
        if (line == null || line.isBlank())
            throw new NumberFormatException("Must supply x, y and direction");
        String[] parts = Arrays.stream(line.trim().split(" "))
                .filter(Predicate.not(String::isBlank))
                .toList().toArray(new String[0]);
        if (parts.length < 3)
            throw new NumberFormatException("Must supply all of x, y and direction");
        if (parts.length > 3)
            throw new NumberFormatException("Should only supply x, y and direction");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Direction direction = Direction.valueOf(parts[2]);
        Location key =  new Location(x, y);
        Rover rover = rovers.getOrDefault(key, null);
        if (rover == null){
            rover = new Rover(this, direction);
            rovers.put(key, rover);
        }
        if (rover.getDirection() != direction)
            throw new NumberFormatException("Can't have two different Rovers at the same location");
        return rover;
    }

    public Location find(Rover rover) {
        Location location = null;
        for (Location key : rovers.keySet()) {
            if (rovers.get(key).equals(rover)){
                location = key;
                break;
            }
        }
        return location;
    }
}

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

    public static final int MAX_DEPTH = 3;
    private final int width;
    private final int height;
    private final Map<Location, Obstacle> obstacles;
    private final Map<Location, Rover> deadRovers;

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

        if (width <= 0) // does not make much sense to have one or less for width
            throw new NumberFormatException("Width must be a natural number");
        if (height <= 0) // does not make much sense to have one or less for height
            throw new NumberFormatException("Height must be a natural number");
        this.width = width;
        this.height = height;

        // create empty Obstacle map
        obstacles = new HashMap<>();
        deadRovers = new HashMap<>();

        // scatter some rocks
        scatterRocks();
    }

    /**
     * Scatter rocks formations across the Plateau
     * Divide the Plateau into chunks and put a single
     * random rock formation (size and location) so that
     * there will always be space to get around them.
     */
    private void scatterRocks() {
    }

    /**
     * Place a Rover in a spare location on the Plateau facing in a random direction.
     * Use similar chunking from the random rocks to ensure we don't get Rovers too
     * close together.
     *
     * @return new Rover
     */
    public Rover addRandomRover(){
        return null;
    }

    @Override
    public String toString() {
        StringBuilder response = new StringBuilder("Plateau is " +
                width + " wide and " +
                height + " high with ");
        if (obstacles.size() > 0){
            response.append(obstacles.size()).append(" Rovers:");
            for (Location key: obstacles.keySet()) {
                Obstacle obstacle = obstacles.get(key);
                response.append("\n").append(obstacle.toString());
            }
        }
        else
            response.append("no Rovers");
        return response.toString();
    }

    /**
     * The inspector.
     * A bit like the toString() method, but used for debugging and testing.
     *
     * @return String representing the internals for testing
     */
    public String inspector() {
        StringBuilder response = new StringBuilder("Plateau{width: " + width + ", height: " + height);
        if (obstacles != null && !obstacles.isEmpty()) {
            response.append(", rovers: {");
            for (Location key: obstacles.keySet()) {
                Obstacle obstacle = obstacles.get(key);
                if (obstacle instanceof Rover rover){
                    Direction direction = rover.getDirection();
                    response.append("Rover{")
                            .append(key.inspector())
                            .append(" ")
                            .append(direction.toString())
                            .append("}, ");
                }
                else {
                    response.append(obstacle.getClass())
                            .append("{")
                            .append(key.inspector())
                            .append(" ")
                            .append(obstacle)
                            .append("}, ");
                }
            }
            response.delete(response.length() - 2, response.length()); // trim extra ", "
        }
        response.append("}");
        return response.toString();
    }

    /**
     * Return an existing, or new Rover at the location and direction given
     *
     * @param line containing the x, y coordinates and direction of the required Rover
     * @return Rover that now exists in the rovers map
     */
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
        Direction direction;
        try{
            direction = Direction.valueOf(parts[2]);
        }
        catch (IllegalArgumentException e){
            throw new NumberFormatException("Third parameter should be a compass direction");
        }
        Location key =  new Location(x, y);
        if (!key.isValid(0, 0, width, height))
            throw new NumberFormatException("Can't place a Rover outside of the Plateau");
        Rover rover = deadRovers.getOrDefault(key, null);
        if (rover != null){
            deadRovers.remove(key, rover);
            throw new NumberFormatException("Unfortunately your rove has been destroyed!");
        } else {
            Obstacle obstacle = obstacles.getOrDefault(key, null);
            if (obstacle != null){
                if (obstacle instanceof Rover){
                    rover = (Rover) obstacle;
                    if (! rover.getDirection().equals(direction))
                        throw new NumberFormatException("Can't have two different Rovers at the same location");
                }
                else{
                    throw new NumberFormatException("Can't place Rover at the same location as something else");
                }
            } else {
                rover = new Rover(this, direction);
                obstacles.put(key, rover);
            }
        }
        return rover;
    }

    /**
     * Return the location of the given Rover
     *
     * @param rover to look for
     * @return Location of where it was found
     */
    public Location find(Rover rover) {
        for (Location key : obstacles.keySet()) {
            if (obstacles.get(key).equals(rover)){
                return key;
            }
        }
        throw new NumberFormatException("Can't have two different Rovers at the same location");
    }

    /**
     * Move a given Rover by the given Delta
     * This will detect, and not move if
     *    reaches the edge of the plateau
     *    collides with another object
     *
     * @param rover to move
     * @param delta how far and what direction
     * @return empty String of ok, else a message
     */
    public String moveRover(Rover rover, Delta delta) {
        String response =  " ";
        Location location = find(rover);
        Location toLocation = new Location(location);
        toLocation.add(delta);
        if (!toLocation.isValid(0, 0, width, height))
            response = "Can't leave the plateau";
        else if (obstacles.get(toLocation) != null)
            response = "Bang! Something is blocking your way";
        else {
            obstacles.remove(location, rover);
            obstacles.put(toLocation,rover);
        }
        return response;
    }

    /**
     * Give the view of the next location from the rover in the direction delta
     *
     * @param rover to base location from
     * @param delta to base direction from
     * @return a blank if nothing there or a rock ("M") or representation of a Rover
     */
    public String viewFromRover(Rover rover, Delta delta){
        Location location = find(rover);
        Location fromLocation = new Location(location);
        fromLocation.add(delta);
        return viewFromHere(fromLocation, delta);
    }

    /**
     * Internal code to do view from a location with an offset in direction delta
     *
     * @param location to base from
     * @param delta to base direction from
     * @return a blank if nothing there or a rock ("M") or representation of a Rover
     */
    private String viewFromHere(Location location, Delta delta){
        String response =  " ";
        Location fromLocation = new Location(location);
        fromLocation.add(delta);
        if (!fromLocation.isValid(0, 0, width, height))
            response = "M"; // use a "mountain" to represent the edge of the plateau
        else if (obstacles.get(fromLocation) != null) { //Something is blocking your way
            Obstacle obstacle = obstacles.get(fromLocation);
            if (obstacle instanceof Rover seen){
                Direction direction = seen.getDirection();
                @SuppressWarnings("SpellCheckingInspection")
                int pos = "NESW".indexOf(direction.toString()); // convert Direction to number
                int rotation = 0;
                if (Delta.S.equals(delta)) {
                    rotation = 2;
                } else if (Delta.E.equals(delta)) {
                    rotation = 3;
                } else if (Delta.W.equals(delta)) {
                    rotation = 1;
                }
                // else Delta.N.equals(delta): rotation = 0; it is 0 by default
                pos = (pos + rotation) % 4;
                response = "" + "A>V<".charAt(pos);
            } else {
                response = obstacle.representation();
            }
        }
        return response;
    }

    /**
     * Give an isometric view of the locations from the rover in the direction delta
     *
     * @param rover to base location from
     * @param delta to base direction from
     * @param maxDepth of view (1 just neighbour) > 1 further away
     * @return a 2D (multiline) view with for each visible location:
     *   a blank if nothing there
     *   a rock ("M")
     *   a representation of a Rover
     */
    public String roverView(Rover rover, Delta delta, int maxDepth){
        StringBuilder view = new StringBuilder();
        Location location = find(rover);
        Location fromLocation = new Location(location);
        Delta left = new Delta(delta);
        left.turn(-1);
        left.add(delta);
        Delta right = new Delta(delta);
        right.turn(1);
        Delta back = new Delta(delta);
        back.turn(1);
        back.turn(1); // face back
        back.add(right);
        for (int i = 0; i < maxDepth; i++) {
            fromLocation.add(left);
        }
        for (int depth = maxDepth; depth >= 0; depth--) {
            view.append("\n"); // start new line
            view.append(" ".repeat(maxDepth - depth)); // indent by depth
            view.append("\\"); // add left view limit
            Location scanLocation = new Location(fromLocation);
            for (int width = 0; width <= depth * 2 ; width++) {
                view.append(viewFromHere(scanLocation, delta));
                scanLocation.add(right);
            }
            view.append("/"); // add right view limit
            fromLocation.add(back);
        }
        return view.toString();
    }

    public String destroy(Rover rover, Delta delta) {
        String response;
        Location location = find(rover);
        Location fromLocation = new Location(location);
        fromLocation.add(delta);
        if (!fromLocation.isValid(0, 0, width, height))
            response = "Cannot fire off of the Plateau - you have wasted your laser!";
        else if (obstacles.get(fromLocation) == null) // nothing there
            response = "Nothing to fire at - you have wasted your laser!";
        else {
            Obstacle obstacle = obstacles.get(fromLocation);
            obstacles.remove(fromLocation, obstacle);
            response = "You have destroyed something!  there may be something you can mine";
            if (obstacle instanceof Rover)
                deadRovers.put(fromLocation, (Rover) obstacle);
            obstacle = obstacle.destroy();
            if (obstacle != null)
                obstacles.put(fromLocation, obstacle);
        }
        return response;
    }

    public String dig(Rover rover, Delta delta) {
        String response;
        Location location = find(rover);
        Location fromLocation = new Location(location);
        fromLocation.add(delta);
        if (!fromLocation.isValid(0, 0, width, height))
            response = "Cannot dig off of the Plateau";
        else if (obstacles.get(fromLocation) == null) // nothing there
            response = "Nothing to dig into";
        else {
            Obstacle obstacle = obstacles.get(fromLocation);
            if (obstacle.canBeMined()){
                int gain = obstacle.getMinerals();
                rover.receiveMinerals(gain);
                obstacles.remove(fromLocation, obstacle);
                response = "You have gained " + gain + " minerals and now have " + rover.getMinerals();
            } else
                response = "This cannot be mined";
        }
        return response;
    }
}

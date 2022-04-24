package com.tinkabell.rover;

import java.util.ArrayList;
import java.util.Random;

/**
 * The Enhanced Plateau extends the simple Plateau
 * It is measured in "units" which are squares as big as can be seen by a Rover (
 */
public class EnhancedPlateau extends Plateau {

    private int width = 0; // in "units"
    private int height = 0; // in "units
    private int extendDirection = 0;
    private static final Random random = new Random();
    public static final int ROCK_SIZE = MAX_DEPTH; // arbitrary size for nw "rocks"

    /**
     * Create an "empty" Plateau that can be expanded as needed.
     */
    public EnhancedPlateau() {
        this("0 0");
    }

    /**
     * Create the Plateau object from String input
     *
     * @param line String containing width and height separated with a space
     */
    private EnhancedPlateau(String line) {
        super(line);
    }

    /**
     * Expand the Plateau to make space for and add ore Rovers
     *
     * @return list of new Rover Locations
     */
    public ArrayList<String> expand(){
        ArrayList<String> list = new ArrayList<>();
        extendDirection++;
        extendDirection %= 4;
        boolean extendWidth = ((extendDirection) / 2) % 2 == 0; // 1=>w, 2=>h, 3=> h, 0=>w
        if (extendWidth)
            width ++;
        else
            height ++;
        extendTo(width * MIN_SEPARATION, height * MIN_SEPARATION);
        if (extendWidth){
            for (int i = 0; i < height; i++) {
                randomRocks(width - 1, i);
                list.add(randomRover(width - 1, i));
            }
        } else {
            for (int i = 0; i < width; i++) {
                randomRocks(i, height - 1);
                list.add(randomRover(i, height - 1));
            }
        }
        return list;
    }

    /**
     * Scatter rocks formations in this "unit".
     *
     * There will be n (1 to ROCK_SIZE) rocks.
     * They will be of size s (ROCK_SIZE - n + 1).
     * If size (s) > 1 then they will cover a "circle" of radius r (1 to s)
     * and be of m (s - r + 1) minerals.
     *
     * @param col unit number
     * @param row unit number
     */
    private void randomRocks(int col, int row) {
        int n = getRandom(ROCK_SIZE) + 1;
        int s = getRandom(ROCK_SIZE - n + 1) + 1;
        int r = getRandom(s) + 1;
        int m = s - r + 1;
        int X = col * MIN_SEPARATION;
        int Y = row * MIN_SEPARATION;
        for (int i = 0; i < n; i++) {
            int x = X + getRandom(MIN_SEPARATION);
            int y = Y + getRandom(MIN_SEPARATION);
            for (int j = 0; j < r; j++) {
                for (int k = 0; k < r; k++) {
                    if (Math.abs(j + k - r + 1) < r)
                        addRock(new Location(x + j - r + 1, y + k - r + 1), m);
                }
            }
        }
    }

    /**
     * Add a Rover randomly to this "unit".
     *
     * only place them where valid and space.
     *
     * @param col unit number
     * @param row unit number
     * @return String of new Rover Location
     */
    private String randomRover(int col, int row) {
        String key = "";
        int X = col * MIN_SEPARATION;
        int Y = row * MIN_SEPARATION;
        while (key.isBlank()){
            int x = X + getRandom(MIN_SEPARATION);
            int y = Y + getRandom(MIN_SEPARATION);
            String direction = "" + "NSEW".charAt(getRandom(4));
            Rover rover = addNewRover(new Location(x, y), Direction.valueOf(direction));
            if (rover != null)
                key = x + " " + y + " " + direction;
        }
        return key;
    }

    /**
     * Get a random number from 0 up to size
     *
     * @param size of maximum number
     * @return number between 0 and up to size
     */
    public int getRandom(int size) {
        return random.nextInt(size);
    }
}

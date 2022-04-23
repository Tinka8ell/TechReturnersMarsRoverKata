package com.tinkabell.rover;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * This is a turn based gamified version of the Mars Rover
 */
public class TurnBasedGame {

    public static void main(String[] argv) throws IOException {
        Controller controller =  new Controller();
        Map<String, String> players = new HashMap<>();
        Queue<String> nextPlayer = new ArrayDeque<>();
        Random random = new Random();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        System.out.println("Welcome to the Mars Rover Turn Based Game");
        System.out.println("Who is playing?  Please enter your names on separate lines");
        String line = "Nothing yet";
        while (!line.isBlank()) {
            line = reader.readLine();
            if (!line.isBlank()) {
                line = line.trim();
                players.put(line, "");
                System.out.println("Welcome '" + line + "' - next player:");
            }
        }
        if (players.isEmpty()){
            System.out.println("Ok, you don't want to play ...");
            return;
        }

        int size = (int) Math.ceil(Math.sqrt((players.size() - 1)) + 1);
        controller.createPlateau(((size * 14) + " " ).repeat(2));
        int x = 0;
        int y = 0;
        for (String player: players.keySet()) {
            String result = "Trying to land";
            String id = "None";
            while (!result.isEmpty()){
                int dx = random.nextInt(12);
                int dy = random.nextInt(12);
                dx += 1 + x;
                dy+= 1 + y;
                id = dx + " " + dy + " " + "NSEW".charAt(random.nextInt(4));
                result = controller.setRover(id);
            }
            players.put(player, id);
            nextPlayer.add(player);
        }
        System.out.println("Your rovers have landed.  Go get them!");
        String response;
        line = "Keep going";
        if (players.size() == 1){
            String player = nextPlayer.remove();
            String location = players.get(player);
            response = controller.setRover(location);
            if (response.isBlank()) {
                while (!line.isBlank()){
                    System.out.println(player + " - your next move:");
                    line = reader.readLine();
                    if (line.isBlank()){
                        response = "OK - enough is enough";
                    } else {
                        response = controller.command(line.toUpperCase());
                        int pos = response.indexOf("\n");
                        if (pos < 0) {
                            location = response;
                        } else {
                            location = response.substring(0, pos);
                            response = response.substring(pos + 1);
                        }
                    }
                    System.out.println(response);
                }
                System.out.println("Final location is: " + location);
            } else {
                System.out.println(response);
            }
        } else {
            while (players.size() > 1) {
                String player = nextPlayer.remove();
                String location = players.get(player);
                response = controller.setRover(location);
                if (response.isBlank()){
                    System.out.println(player + " - your turn:");
                    line = reader.readLine();
                    if (line.isBlank())
                        line = "V";
                    response = controller.command(line.toUpperCase());
                    int pos = response.indexOf("\n");
                    if (pos < 0) {
                        location = response;
                    } else {
                        location = response.substring(0, pos);
                        response = response.substring(pos + 1);
                    }
                    players.put(player, location); // update location
                    nextPlayer.add(player);
                } else {
                    players.remove(player);
                    System.out.println(player + ", sorry, but you seem to be destroyed!");
                }
                System.out.println(response);
            }
        }
    }
}

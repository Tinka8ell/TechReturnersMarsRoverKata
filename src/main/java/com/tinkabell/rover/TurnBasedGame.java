package com.tinkabell.rover;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;

/**
 * This is a turn based gamified version of the Mars Rover
 */
public class TurnBasedGame {

    private final ArrayList<String> unassigned;
    private final Controller controller =  new Controller();
    private final Queue<Player> nextPlayer = new ArrayDeque<>();

    public TurnBasedGame() {
        controller.createEnhancedPlateau();
        unassigned = controller.expandPlateau(); // gives 1 x 1
        unassigned.addAll(controller.expandPlateau()); // gives 1 x 2
        unassigned.addAll(controller.expandPlateau()); // gives 2 x 2
    }

    public String getWinner(){
        Player player = getNext();
        return "Well done " + player + " you survived with " + player.getMaterials(controller);
    }

    public String playerInteraction(Player player, BufferedReader reader, PrintStream writer){
        String line;
        String response = execute(player);
        writer.println(response);
        if (player.stillAlive()){
            writer.println("Commands to send:");
            try {
                line = reader.readLine().trim();
            } catch (IOException e) {
                // should never happen, so treat as missed turn
                line = "";
            }
            player.setCommand(line);
        } else {
            line = ""; // end the game
        }
        return line;
    }

    private String execute(Player player) {
        return player.execute(controller);
    }

    public void addPlayer(String name){
        String id = "";
        while (id.isBlank()) {
            if (unassigned.size() == 0){
                unassigned.addAll(controller.expandPlateau()); // gives more
            }
            id = unassigned.remove(controller.getRandom(unassigned.size()));
            String response = controller.setRover(id);
            if (!response.isBlank())
                id = ""; // error so try another
        }
        Player player = new Player(name, id);
        addLast(player);
    }

    public int getPlayerCount(){
        return nextPlayer.size();
    }

    public void addLast(Player player) {
        nextPlayer.add(player);
    }

    public Player getNext() {
        return nextPlayer.remove();
    }

    public static void main(String[] argv) throws IOException {
        TurnBasedGame game = new TurnBasedGame();
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));

        System.out.println("Welcome to the Mars Rover Turn Based Game");
        System.out.println("Who is playing?  Please enter your names on separate lines");
        String line = "Nothing yet";
        while (!line.isBlank()) {
            line = reader.readLine();
            if (!line.isBlank()) {
                line = line.trim();
                game.addPlayer(line);
                System.out.println("Welcome '" + line + "' - next player:");
            }
        }
        if (game.getPlayerCount() == 0){
            System.out.println("Ok, you don't want to play ...");
            return;
        }

        if (game.getPlayerCount() == 1){
            Player player = game.getNext();
            System.out.println(player + " - your rover has landed.  Go get them rocks!");
            line = "Keep going";
            while (!line.isBlank()){
                System.out.println("Receiving:");
                line = game.playerInteraction(player, reader, System.out);
            }
        } else {
            System.out.println("Your rovers have landed.  Go get them!");
            while (game.getPlayerCount() > 1) {
                Player player = game.getNext();
                System.out.println(player + " - receiving:");
                game.playerInteraction(player, reader, System.out);
                if (player.stillAlive()){
                    game.addLast(player); // you get another go!
                }
            }
            System.out.println(game.getWinner());
        }
    }

}

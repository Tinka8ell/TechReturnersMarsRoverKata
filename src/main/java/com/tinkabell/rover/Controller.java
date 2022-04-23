package com.tinkabell.rover;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @return empty String if successful, or an "Error ..." message
     */
    public String createPlateau(String line){
        String response = ""; // no error
        try {
            mars = new Plateau(line);
        }
        catch (NumberFormatException e){
            response = "Error: " + e.getMessage();
        }
        return response;
    }

    /**
     * The inspector.
     * A bit like the toString() method, but used for debugging and testing.
     *
     * @return String representing the internals for testing
     */
    protected String inspector(){
        return "Controller{mars: " +
                (mars == null ? "null": mars.inspector()) +
                "}";
    }

    /**
     * Set the current Rover from a Rover string
     *
     * @param line containing location and direction of a Rover
     * @return empty String if successful, or an "Error ..." message
     */
    public String setRover(String line){
        String response = "";
        if (mars == null) {
            response = "Error: Can't get Rover until we have a Plateau!";
        } else {
            try {
                currentRover = mars.getRover(line);
            }
            catch (NumberFormatException e) {
                line = line.trim().toUpperCase();
                if (line.equals("?") || line.equals("H")){
                    response = help();
                } else {
                    response =  "Error: " + e.getMessage();
                }
            }
        }
        return response;
    }

    /**
     * Give commands to the current Rover
     * It will "safely" crash into the edges or other objects
     * and stop just before with an extended message
     *
     * @param commandString of action codes
     * @return String of final location of the Rover (toString)
     * appended with any error message
     */
    public String command(String commandString) {
        String response = "";
        if (currentRover == null) {
            commandString = commandString.trim().toUpperCase();
            if (commandString.equals("?") || commandString.equals("H"))
                response = help();
            else if (commandString.equals("P"))
                response = showPlateau();
            else
                response = "Error: Must select a Rover before sending commands";
        } else {
            Pattern reserved = Pattern.compile("[Pp?Hh]");
            Matcher matcher = reserved.matcher(commandString);
            if (matcher.find()){
                int pos = matcher.start();
                if (pos > 0){
                    response = currentRover.command(commandString.substring(0, pos));
                }
                if (response.isBlank()){
                    response = currentRover.toString() + "\n";
                    String controllerCommand = commandString.substring(pos, pos + 1);
                    switch (controllerCommand.toUpperCase()){
                        case "?", "H" -> response += help();
                        case "P" -> response += showPlateau();
                    }
                }
            } else {
                response = currentRover.command(commandString);
                try{
                    if (response.isBlank())
                        response = currentRover.toString();
                    else
                        response = currentRover.toString() + "\n" + response;
                } catch (NumberFormatException e){
                    response = e.getMessage();
                }
            }
        }
        return response;
    }

    /**
     * Return a help string
     *
     * @return help message
     */
    private String help() {
        String response = """
                Rover Controller
                Commands accepted:
                   ? or H - this help test
                   x y c - select new Rover with coordinates x, y and compass direction c
                   P - show the Plateau
                """;
        if (currentRover != null){
            response += "Current Rover (" +
                    currentRover +
                    ") accepts:\n" +
                    currentRover.help();
        }
        return response;
    }

    /**
     * Output the current state of the Plateau
     *
     * @return String of Plateau info
     */
    public String showPlateau() {
        return mars.toString();
    }
}

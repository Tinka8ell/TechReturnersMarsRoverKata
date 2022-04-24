package com.tinkabell.rover;

/**
 * A representation of a player
 * Contains its name, its id (location and direction)
 * Prompts for input
 * Replies with response to that or other things
 */
public class Player {

    private final String name;
    private String id;
    private String commands = "";
    private String response = "";
    private boolean isAlive = true;


    public Player(String name, String id) {
        this.name = name;
        this.id = id;
        setCommand("");
        System.out.println("Created player " + name + " at " + id);
    }

    public String getMaterials(Controller controller){
        response = controller.setRover(id);
        if (response.isBlank()){
            response = controller.command(commands);
            int nl = response.indexOf('\n');
            response = response.substring(nl + 1);
        } else
            isAlive = false;
        return response;
    }

    @Override
    public String toString() {
        return name;
    }

    public String execute(Controller controller){
        response = controller.setRover(id);
        if (response.isBlank()){
            response = controller.command(commands);
            int nl = response.indexOf('\n');
            if (nl > 0){
                id = response.substring(0, nl); // new location
                response = response.substring(nl + 1); // response to return
            } else {
                System.out.println("Unexpected response: "+ response);
                id = response; // new location
                response = ""; // response to return
            }
        } else
            isAlive = false;
        return response;
    }

    public boolean stillAlive(){
        return isAlive;
    }

    public void setCommand(String line) {
        commands = (line + "V").trim().toUpperCase();
    }
}

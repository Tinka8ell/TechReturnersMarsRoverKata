package com.tinkabell.rover;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MarsRover {

    public static void main(String[] argv) throws IOException {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in));
        Controller controller =  new Controller();
        System.out.println("Welcome to the mars Rover project");
        System.out.println("Enter the size of the plateau in format: x y, or blank line to end");
        String line = "";
        String response = "Waiting";
        while (!response.isBlank()) {
            line = reader.readLine();
            if (line.isBlank())
                response = "";
            else
                response = controller.createPlateau(line.toUpperCase());
            if (! response.isBlank())
                System.out.println(response);
        }
        if (!line.isBlank()){
            System.out.println("Enter your Rover starting coordinates in format: 'x y h', " +
                    "where h is a compass heading");
            response = "Waiting";
            while (!response.isBlank()) {
                line = reader.readLine();
                if (line.isBlank())
                    response = "";
                else
                    response = controller.setRover(line.toUpperCase());
                if (! response.isBlank())
                    System.out.println(response);
            }
            if (!line.isBlank()) {
                System.out.println("Now enter command actions or new Rover coordinates, or blank line to end");
                while (!line.isBlank()) {
                    line = reader.readLine();
                    if (line.isBlank())
                        response = "Thank you and goodbye";
                    else {
                        response = controller.setRover(line.toUpperCase());
                        if (response.isBlank())
                            response = "Ok"; // show new Rover accepted
                        else // not Rover coordinates, so try command:
                            response = controller.command(line.toUpperCase());
                    }
                    System.out.println(response);
                }
            }
        }
    }
}

package driver;

import util.Game;
import util.IllegalChessMoveException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter the players name and elo ratings: ");
        String p1Name = s.next();
        int p1Rating = s.nextInt();
        String p2Name = s.next();
        int p2Rating = s.nextInt();
        s.nextLine();
        Game game = new Game(p1Name, p1Rating, p2Name, p2Rating);
        while (true) {
            System.out.println("Please enter a command (mv, cp, print, or quit):");
            String command = s.nextLine();
            String[] tokens = command.split(" ");
            if (tokens[0].equals("quit"))
                break;
            if (tokens[0].equals("print"))
                System.out.println(game);
            else if (tokens[0].equals("mv") || tokens[0].equals("cp")) {
                if (tokens.length != 3) {
                    System.out.println("Invalid command!");
                    continue;
                }
                tokens[1] = tokens[1].toLowerCase();
                tokens[2] = tokens[2].toLowerCase();
                if (!isValidArgument(tokens[1]) || !isValidArgument(tokens[2])) {
                    System.out.println("Invalid command!");
                    continue;
                }
                if (tokens[0].equals("mv"))
                    try {
                        game.move(tokens[1], tokens[2]);
                    } catch (IllegalChessMoveException e) {
                        System.out.println("Proper illegal chess move message!");
                    }
                else
                    try {
                        game.capture(tokens[1], tokens[2]);
                    } catch (IllegalChessMoveException e) {
                        System.out.println("Proper message!");
                    }
            } else
                System.out.println("Invalid command!");
        }

    }

    private static boolean isValidArgument(String arg) {
        if (arg.length() != 2)
            return false;
        return arg.charAt(0) >= 'a' && arg.charAt(0) <= 'h' && arg.charAt(1) >= '1' && arg.charAt(1) <= '8';
    }

}

package TicTacToe;

import java.io.PrintStream;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final PrintStream out = System.out;
        final Scanner in = new Scanner(System.in);

        int result;
        int m = 0;
        int n = 0;
        int k = 0;
        int amountOfGames = 0;
        System.out.println("Welcome to the new game! Write m, n, k and amount of games");

        String a, b, c, d;

        while (true) {
            a = in.next();
            b = in.next();
            c = in.next();
            d = in.next();

            try {
                m = Integer.parseInt(a);
                n = Integer.parseInt(b);
                k = Integer.parseInt(c);
                amountOfGames = Integer.parseInt(d);

                if (amountOfGames <= 0 || m <= 0 || n <= 0 || k <= 0 || m < k && n < k) {
                    System.out.println("Incorrect input. Try again");
                }
                else {
                    break;
                }
            } catch (NumberFormatException e) {
                out.println("Wrong input. Try again");
            }

        }

        int amountFirstWon = 0;
        int amountSecondWon = 0;

        final Game game = new Game(false, new HumanPlayer(), new SequentialPlayer(n, m));

        do {
            result = game.play(new TicTacToeBoard(m, n, k));
            System.out.println("Game result: " + result + " player won");
            if (result == 1) {
                amountFirstWon++;
            }
            else if (result == 2) {
                amountSecondWon++;
            }
        } while (amountFirstWon != amountOfGames && amountSecondWon != amountOfGames);

        System.out.println("Match result: ");

        if (amountFirstWon == amountOfGames) {
            System.out.print("first");
        }
        else {
            System.out.print("second");
        }

        System.out.println(" player won!");

        in.close();
        out.close();
    }
}

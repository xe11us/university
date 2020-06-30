package TicTacToe;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private final PrintStream out;
    private final Scanner in;

    public HumanPlayer(final PrintStream out, final Scanner in) {
        this.out = out;
        this.in = in;
    }

    public HumanPlayer() { this(System.out, new Scanner(System.in)); }

    private boolean isInt(String toCheck) {
        int pos = 0;
        if (toCheck.charAt(0) == '-' && toCheck.length() > 1) {
            pos++;
        }

        for (; pos < toCheck.length(); pos++) {
            if (!Character.isDigit(toCheck.charAt(pos))) {
                return false;
            }
        }
        return true;
    }

    private int myParseInt(String toParse) {
        int pos = 0;
        if (toParse.charAt(0) == '-') {
            pos++;
        }

        int answer = 0;

        for (; pos < toParse.length(); pos++) {
            answer = answer * 10 + (toParse.charAt(pos) - '0');
        }

        return toParse.charAt(0) == '-'? -answer : answer;
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            out.println("Position");
            out.println(position);
            out.println(cell + "'s move");
            out.println("Enter row and column");

            int x = 0;
            int y = 0;

            String a = in.next();
            String b = in.next();
            while (!isInt(a) || !isInt(b)) {
                out.println("Wrong input. Try again");
                a = in.next();
                b = in.next();
            }

            x = myParseInt(a);
            y = myParseInt(b);

            final Move move = new Move(x, y, cell);
            if (position.isValid(move)) {
                return move;
            }
            out.println("Move " + move + " is invalid");
        }
    }
}

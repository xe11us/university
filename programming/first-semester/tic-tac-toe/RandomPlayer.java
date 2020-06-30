package TicTacToe;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random;

    private int height;
    private int width;

    public RandomPlayer(final Random random) {
        this.random = random;
    }

    public RandomPlayer(int height, int width) {
        this(new Random());
        this.height = height;
        this.width = width;
    }

    @Override
    public Move move(final Position position, final Cell cell) {
        while (true) {
            int row = random.nextInt(height);
            int column = random.nextInt(width);
            final Move move = new Move(row, column, cell);
            if (position.isValid(move)) {
                return move;
            }
        }
    }
}

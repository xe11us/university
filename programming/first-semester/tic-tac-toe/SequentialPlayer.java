package TicTacToe;

public class SequentialPlayer implements Player {

    private int height;
    private int width;

    SequentialPlayer(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public Move move(final Position position, final Cell cell) {

        for (int row = 0; row < height; row++) {
            for (int column = 0; column < width; column++) {
                final Move move = new Move(row, column, cell);
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new IllegalStateException("No valid moves");
    }
}

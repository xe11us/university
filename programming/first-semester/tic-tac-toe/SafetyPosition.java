package TicTacToe;

public class SafetyPosition implements Position {

    private final Position position;

    public SafetyPosition(final Position position) {
        this.position = position;
    }

    @Override
    public boolean isValid(final Move move) {
        return position.isValid(move);
    }

    @Override
    public Cell getCell(final int row, final int column) {
        return position.getCell(row, column);
    }

    public String toString() {
        return position.toString();
    }
}

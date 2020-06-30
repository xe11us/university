package TicTacToe;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, Character> SYMBOLS = Map.of(
            Cell.X, 'X',
            Cell.O, 'O',
            Cell.E, '.'
    );

    private final Cell[][] cells;
    private Cell turn;
    private final int height;
    private final int width;
    private final int amountToWin;
    private int amountOfEmpty;

    public TicTacToeBoard(int height, int width, int amountToWin) {
        this.height = height;
        this.width = width;
        this.amountToWin = amountToWin;
        this.amountOfEmpty = height * width;
        this.cells = new Cell[height][width];
        for (Cell[] row : cells) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    @Override
    public Position getPosition() {
        return new SafetyPosition(this);
    }

    @Override
    public Cell getCell() {
        return turn;
    }

    private boolean isValid(int x, int y) {
        return 0 <= x && x < height && 0 <= y && y < width;
    }

    private int countCells(int i, int j, int delta_x, int delta_y) {
        int stepNumber = 0;
        int answer = 0;
        int x = i;
        int y = j;

        while (isValid(x + delta_x, y + delta_y)) {
            x += delta_x;
            y += delta_y;
            stepNumber++;

            if (cells[x][y] == turn) {
                answer++;
            }
            else {
                break;
            }

            if (stepNumber >= amountToWin) {
                break;
            }
        }

        return answer;
    }

    @Override
    public Result makeMove(final Move move) {

        cells[move.getRow()][move.getColumn()] = move.getValue();
        amountOfEmpty--;

        int row = move.getRow();
        int column = move.getColumn();

        int up = countCells(row, column, -1, 0);
        int down = countCells(row, column, 1, 0);
        int left = countCells(row, column, 0, -1);
        int right = countCells(row, column, 0, 1);
        int leftUpDiag = countCells(row, column, -1, -1);
        int rightUpDiag = countCells(row, column, -1, 1);
        int leftDownDiag = countCells(row, column, 1, -1);
        int rightDownDiag = countCells(row, column, 1, 1);

        if (up + down + 1 >= amountToWin || left + right + 1 >= amountToWin
                || leftUpDiag + rightDownDiag + 1 >= amountToWin || rightUpDiag + leftDownDiag  + 1 >= amountToWin) {
            return Result.WIN;
        }

        if (amountOfEmpty == 0) {
            return Result.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return Result.UNKNOWN;
    }

    @Override
    public boolean isValid(final Move move) {
        return 0 <= move.getRow() && move.getRow() < height
                && 0 <= move.getColumn() && move.getColumn() < width
                && cells[move.getRow()][move.getColumn()] == Cell.E
                && turn == getCell();
    }

    @Override
    public Cell getCell(final int r, final int c) {
        return cells[r][c];
    }

    @Override
    public String toString() {
        final StringBuilder sb;

        StringBuilder countSb = new StringBuilder(" ");

        for (int i = 0; i < width; i++) {
            char current = '0';
            current += i;
            countSb.append(current);
        }

        sb = countSb;

        for (int r = 0; r < height; r++) {
            sb.append("\n");
            sb.append(r);
            for (int c = 0; c < width; c++) {
                sb.append(SYMBOLS.get(cells[r][c]));
            }
        }
        return sb.toString();
    }
}

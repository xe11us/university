package TicTacToe;

public interface Board {
    Position getPosition();
    Cell getCell();
    Result makeMove(Move move);
}
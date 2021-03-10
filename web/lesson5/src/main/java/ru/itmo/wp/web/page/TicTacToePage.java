package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class TicTacToePage {

    private void action(HttpServletRequest request, Map<String, Object> view) {
        State state = getState(request);
        if (state == null) {
            newGame(request, view);
        }
        view.put("state", state);
    }

    private void onMove(HttpServletRequest request, Map<String, Object> view) {
        State state = getState(request);

        String cell = request.getParameterMap().keySet().stream()
                .filter(x -> x.matches("cell_\\d{2}"))
                .findFirst()
                .orElse(null);

        if (Strings.isNullOrEmpty(cell)) {
            return;
        }

        state.nextState(cell.charAt(5) - '0', cell.charAt(6) - '0');

        view.put("state", state);
        throw new RedirectException("/ticTacToe");
    }

    private void newGame(HttpServletRequest request, Map<String, Object> view) {
        request.getSession().setAttribute("state", new State(3));
        view.put("state", getState(request));
    }

    private State getState(HttpServletRequest request) {
        return (State) request.getSession().getAttribute("state");
    }

    public static class State {
        private final int size;
        private int emptyCellCount;
        private Phase phase;
        private boolean crossesMove;
        private final Cell[][] cells;

        State(int size) {
            this.size = size;
            this.phase = Phase.RUNNING;
            this.crossesMove = true;
            this.cells = new Cell[size][size];
            this.emptyCellCount = size * size;
        }

        public int getSize() {
            return size;
        }

        public Phase getPhase() {
            return phase;
        }

        public boolean isCrossesMove() {
            return crossesMove;
        }

        public Cell[][] getCells() {
            return cells;
        }

        public void nextState(int row, int column) {
            if (phase != Phase.RUNNING || !isFreeCell(row, column)) {
                return;
            }
            makeStep(row, column);
            checkForEndGame(row, column);
            crossesMove = !crossesMove;
        }

        private void makeStep(int row, int column) {
            cells[row][column] = crossesMove ? Cell.X : Cell.O;
            emptyCellCount--;
        }

        private void checkForEndGame(int row, int column) {
            boolean win = false;

            for (int i = -1; i <= 1; ++i) {
                int sum = countCells(row, column, i, -1) + countCells(row, column, -i, 1) - 1;
                if (sum >= size) {
                    win = true;
                }
            }

            if (win || countCells(row, column, -1, 0) +
                    countCells(row, column, 1, 0) - 1 >= size) {
                phase = crossesMove ? Phase.WON_X : Phase.WON_O;
            } else if (emptyCellCount == 0) {
                phase = Phase.DRAW;
            }
        }

        private int countCells(int row, int column, int deltaRow, int deltaColumn) {
            return isValid(row, column) && cells[row][column] == (crossesMove ? Cell.X : Cell.O) ?
                    countCells(row + deltaRow, column + deltaColumn, deltaRow, deltaColumn) + 1 : 0;
        }

        private boolean isValid(int row, int column) {
            return 0 <= row && row < size && 0 <= column && column < size;
        }

        private boolean isFreeCell(int row, int column) {
            return isValid(row, column) && cells[row][column] == null;
        }
    }

    public enum Phase {
        RUNNING, WON_X, WON_O, DRAW
    }

    public enum Cell {
        X, O
    }
}
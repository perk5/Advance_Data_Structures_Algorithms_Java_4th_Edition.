import java.util.*;

public class MiniMaxTicTacToe {

    public static class PlayGame {

        char[][] board = new char[3][3];
        private char player = 'X';

        public void switchPlayer() {
            if (player == 'X') {
                player = 'O';
                return;
            }
            player = 'X';
        }

        public void drawEmptyboard() {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    board[i][j] = '-';
                }
            }
        }

        public void drawboard() {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    System.out.print(board[i][j]);
                    System.out.print(" ");
                }
                System.out.println();
            }
            System.out.println();
        }

        public void humanMove(int i, int j) {

            if (board[i][j] != '-') {
                throw new IllegalArgumentException("The Space is already filled..!");
            }

            board[i][j] = player;

        }

        public boolean isBoardFull() {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board.length; j++) {
                    if (board[i][j] == '-') {
                        return false;
                    }
                }
            }
            return true;
        }

        public void computerMove() {

            int bestValue = Integer.MAX_VALUE;
            int bestRow = -1;
            int bestCol = -1;

            if(isBoardFull()){
                return;
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    if (board[i][j] == '-') {

                        // try move
                        board[i][j] = 'O';

                        // evaluate move (now X's turn → isMax = true)
                        int moveValue = minimax(true);

                        // undo move
                        board[i][j] = '-';

                        // choose minimum (because O is minimizing)
                        if (moveValue < bestValue) {
                            bestValue = moveValue;
                            bestRow = i;
                            bestCol = j;
                        }
                    }
                }
            }

            // play best move
            board[bestRow][bestCol] = 'O';
        }

        public int minimax(boolean isMax) {

            if (isWin('X'))
                return 1;
            if (isWin('O'))
                return -1;

            if (isBoardFull())
                return 0;

            if (isMax) { // X's turn
                int best = Integer.MIN_VALUE;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        if (board[i][j] == '-') {
                            board[i][j] = 'X';

                            int val = minimax(false);

                            board[i][j] = '-'; // undo

                            best = Math.max(best, val);
                        }
                    }
                }

                return best;
            }

            else { // O's turn
                int best = Integer.MAX_VALUE;

                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {

                        if (board[i][j] == '-') {
                            board[i][j] = 'O';

                            int val = minimax(true);

                            board[i][j] = '-'; // undo

                            best = Math.min(best, val);

                        }
                    }

                }

                return best;
            }
        }

        public void playGame(int i, int j) {
            board[i][j] = 'X';

            // computer responds (O)
            computerMove();

        }

        public boolean isWin(char mark) {

            // rows
            for (int i = 0; i < 3; i++) {
                if (board[i][0] == mark &&
                        board[i][1] == mark &&
                        board[i][2] == mark) {
                    return true;
                }
            }

            // columns
            for (int j = 0; j < 3; j++) {
                if (board[0][j] == mark &&
                        board[1][j] == mark &&
                        board[2][j] == mark) {
                    return true;
                }
            }

            // diagonal (top-left to bottom-right)
            if (board[0][0] == mark &&
                    board[1][1] == mark &&
                    board[2][2] == mark) {
                return true;
            }

            // diagonal (top-right to bottom-left)
            if (board[0][2] == mark &&
                    board[1][1] == mark &&
                    board[2][0] == mark) {
                return true;
            }

            return false;
        }

    }

    public static void main(String args[]) {
        PlayGame game = new PlayGame();
        game.drawEmptyboard();
        game.drawboard();

        game.playGame(0, 0);
        game.playGame(1, 2);
        game.playGame(2, 1);
        // game.playGame(2, 1);
        // game.playGame(0, 2);
        // game.playGame(1, 0);
        // game.playGame(0, 1);
        // game.playGame(0, 2);
        // game.playGame(2, 2);
        // game.playGame(2, 0);
        game.drawboard();
    }
}
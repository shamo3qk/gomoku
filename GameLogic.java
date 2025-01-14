import javax.swing.JOptionPane;
import javax.swing.JFrame;

public class GameLogic {
    private char[][] board;
    private char currentPlayer;

    public GameLogic(int row, int col) {
        board = new char[row][col];
        initBoard();
        currentPlayer = 'X';
    }

    private void initBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public char getCurrentPlayer() {
        return currentPlayer;
    }

    public char getCell(int row, int col) {
        return board[row][col];
    }

    public boolean makeMove(int row, int col) {
        if (board[row][col] == ' ') {
            board[row][col] = currentPlayer;
            switchPlayer();
            return true;
        }
        return false;
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    public void checkWinner() {
        String status = "continued";
        JFrame broadcast = new JFrame("Broadcast Message App");

        // check row
        for (char[] row : board) {
            if (countOccurrences(row, 'O') >= 5) {
                status = "O win";
            } else if (countOccurrences(row, 'X') >= 5) {
                status = "X win";
            }
        }

        // check column
        for (int col = 0; col < 9; col++) {
            if (countOccurrences(getColumn(board, col), 'O') >= 5) {
                status = "O win";
            } else if (countOccurrences(getColumn(board, col), 'X') >= 5) {
                status = "X win";
            }
        }

        // check diagonal
        char winner = checkDiagonal();
        if (winner == 'O')
            status = "O win";
        else if (winner == 'X')
            status = "X win";

        // check is game tied ?
        if (isBoardFull(board)) {
            status = "tied";
        }

        if (status != "continued") {
            JOptionPane.showMessageDialog(broadcast, status, "廣播訊息", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }

    public char checkDiagonal() {
        // Check diagonals from top-left to bottom-right
        for (int i = 0; i <= 4; i++) {
            for (int j = 0; j <= 4; j++) {
                char winner = checkDiagonalFromTopLeft(i, j);
                if (winner != ' ') {
                    return winner;
                }
            }
        }

        // Check diagonals from top-right to bottom-left
        for (int i = 0; i <= 4; i++) {
            for (int j = 4; j < 9; j++) {
                char winner = checkDiagonalFromTopRight(i, j);
                if (winner != ' ') {
                    return winner;
                }
            }
        }

        return ' '; // No winner
    }

    private char checkDiagonalFromTopLeft(int row, int col) {
        char player = board[row][col];
        if (player == ' ') {
            return ' '; // Empty cell, no connection possible
        }

        for (int i = 0; i < 5; i++) {
            if (board[row + i][col + i] != player) {
                return ' '; // Not a continuous sequence of the same player's pieces
            }
        }

        return player; // Winner found
    }

    private char checkDiagonalFromTopRight(int row, int col) {
        char player = board[row][col];
        if (player == ' ') {
            return ' '; // Empty cell, no connection possible
        }

        for (int i = 0; i < 5; i++) {
            if (board[row + i][col - i] != player) {
                return ' '; // Not a continuous sequence of the same player's pieces
            }
        }

        return player; // Winner found
    }

    private int countOccurrences(char[] array, char target) {
        int count = 0;
        int maxCount = 0;

        for (char ch : array) {
            if (ch == target) {
                count++;
                maxCount = (count > maxCount) ? count : maxCount;
            } else {
                count = 0;
            }
        }

        return maxCount;
    }

    private char[] getColumn(char[][] board, int col) {
        char[] column = new char[9];
        for (int row = 0; row < 9; row++) {
            column[row] = board[row][col];
        }
        return column;
    }

    private boolean isBoardFull(char[][] board) {
        for (char[] row : board) {
            for (char cell : row) {
                if (cell == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}

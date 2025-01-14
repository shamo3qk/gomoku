import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameBoard extends JFrame {
    private JButton[][] boardButtons;
    GameLogic gameReferee;

    public GameBoard() {
        setTitle("五子棋");
        setSize(900, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int rows = 9;
        int cols = 9;

        gameReferee = new GameLogic(rows, cols);
        setLayout(new GridLayout(rows, cols));

        boardButtons = new JButton[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boardButtons[i][j] = new JButton();
                boardButtons[i][j].setBackground(Color.WHITE);
                boardButtons[i][j].addActionListener(new ButtonClickListener(i, j));
                add(boardButtons[i][j]);
            }
        }

        setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (gameReferee.getCell(row, col) == ' ') {
                if (gameReferee.getCurrentPlayer() == 'X') {
                    boardButtons[row][col].setBackground(Color.BLACK);
                } else {
                    boardButtons[row][col].setBackground(Color.LIGHT_GRAY);
                }
                // update board & switch player
                gameReferee.makeMove(row, col);
                gameReferee.checkWinner();
            }
        }
    }
}

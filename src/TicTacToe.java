import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600, boardHeight = 650;

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    boolean gameOver = false;
    int turns = 0;

    private void checkWinner(){
        // horizontal
        for(int r = 0; r < 3; r++){
            if (board[r][0].getText().isEmpty()) continue;
            else if (board[r][0].getText().equals(board[r][1].getText()) && board[r][1].getText().equals(board[r][2].getText())){
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        // vertical
        for (int c = 0; c < 3; c++){
            if (board[0][c].getText().isEmpty()) continue;
            else if (board[0][c].getText().equals(board[1][c].getText()) && board[1][c].getText().equals(board[2][c].getText())){
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        // diagonally
        if(board[0][0].getText().equals(board[1][1].getText()) &&
           board[1][1].getText().equals(board[2][2].getText()) &&
           !board[0][0].getText().isEmpty()){
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        // anti-diagonally
        if (board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText()) &&
            !board[0][2].getText().isEmpty()){
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        // tie
        if (turns >= 9){
            for (int c = 0; c < 3; c++){
                for (int r = 0; r < 3; r++){
                    setTie(board[r][c]);
                }
            }
            gameOver = true;
        }
    }

    void setTie(JButton tile){
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
        textLabel.setText("It's a tie!");
    }

    void setWinner(JButton tile){
        tile.setForeground(Color.GREEN);
        tile.setBackground(Color.gray);
        textLabel.setText(currentPlayer + " wins!");
    }

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.addKeyListener(new Keychecker(this));

        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel, BorderLayout.CENTER);

        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) {
                            return;
                        }
                        JButton tile = (JButton) e.getSource();
                        if (!tile.getText().isEmpty()) {
                            return;
                        }
                        tile.setText(currentPlayer);
                        turns++;
                        checkWinner();
                        if (gameOver) {
                            return;
                        }
                        currentPlayer = currentPlayer.equals(playerX) ? playerO:playerX;
                        textLabel.setText(currentPlayer + "'s turn");

                    }
                });
            }
        }
    }
    void resetBoard(){
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setText("");
                gameOver = false;
            }
        }
    }
}
class Keychecker extends KeyAdapter {
    TicTacToe ticTacToe; // Instance of TicTacToe

    // Constructor to initialize TicTacToe instance
    public Keychecker(TicTacToe ticTacToe) {
        this.ticTacToe = ticTacToe;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        char ch = event.getKeyChar();

        // Check if 'R' key is pressed
        if (ch == 'R' || ch == 'r') {
            // Call resetBoard() method of TicTacToe
            ticTacToe.resetBoard();
        }
        if (ch == 'E' || ch == 'e') {
            ticTacToe.frame.dispose();
        }
    }
}


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;
    int boardHeight = 700; // Aumentado para incluir botão

    JFrame frame = new JFrame("Jogo da Velha");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel controlPanel = new JPanel(); // Novo painel para o botão
    JButton resetButton = new JButton("Reiniciar"); // Botão de reset
    JButton[][] board = new JButton[3][3];

    String playerX = "X";
    String playerO = "O";
    String currentplayer = playerX;
    boolean gameOver = false;
    int turns = 0;

    TicTacToe() {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Label de status do jogo
        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.WHITE);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Vez de " + currentplayer);
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Painel do tabuleiro
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.DARK_GRAY);
        frame.add(boardPanel, BorderLayout.CENTER);

        // Criação dos botões do tabuleiro
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
                        JButton tile = (JButton) e.getSource();
                        if (gameOver) return;

                        if (tile.getText().equals("")) {
                            tile.setText(currentplayer);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentplayer = currentplayer.equals(playerX) ? playerO : playerX;
                                textLabel.setText("Vez de " + currentplayer);
                            }
                        }
                    }
                });
            }
        }

        // Painel inferior com botão de reiniciar
        controlPanel.setLayout(new FlowLayout());
        resetButton.setFont(new Font("Arial", Font.PLAIN, 24));
        resetButton.setFocusable(false);
        resetButton.addActionListener(e -> resetGame());
        controlPanel.add(resetButton);
        frame.add(controlPanel, BorderLayout.SOUTH);
    }

    // Verifica se alguém venceu ou se houve empate
    void checkWinner() {
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText().equals("")) continue;

            if (board[r][0].getText().equals(board[r][1].getText()) &&
                board[r][1].getText().equals(board[r][2].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]);
                }
                gameOver = true;
                return;
            }
        }

        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText().equals("")) continue;

            if (board[0][c].getText().equals(board[1][c].getText()) &&
                board[1][c].getText().equals(board[2][c].getText())) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]);
                }
                gameOver = true;
                return;
            }
        }

        if (board[0][0].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][2].getText()) &&
            !board[0][0].getText().equals("")) {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]);
            }
            gameOver = true;
            return;
        }

        if (board[0][2].getText().equals(board[1][1].getText()) &&
            board[1][1].getText().equals(board[2][0].getText()) &&
            !board[0][2].getText().equals("")) {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]);
                }
            }
            textLabel.setText("Deu velha!");
            gameOver = true;
        }
    }

    void setWinner(JButton tile) {
        tile.setForeground(Color.green);
        tile.setBackground(Color.black);
        textLabel.setText(currentplayer + " venceu!");
    }

    void setTie(JButton tile) {
        tile.setForeground(Color.orange);
        tile.setBackground(Color.gray);
    }

    // Reseta o jogo
    void resetGame() {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = board[r][c];
                tile.setText("");
                tile.setBackground(Color.darkGray);
                tile.setForeground(Color.white);
            }
        }
        currentplayer = playerX;
        turns = 0;
        gameOver = false;
        textLabel.setText("Vez de " + currentplayer);
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}

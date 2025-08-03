import javax.swing.*;   // Import necessary classes for GUI components
import java.awt.*;  // Import necessary classes for GUI components
import java.awt.event.*;    // Import necessary classes for GUI components and event handling

public class GameBoard extends JFrame {     //creates the game board
    private JButton[] buttons = new JButton[9];// Array to hold the buttons for each cell
    private char[] board = new char[9];// Array to hold the game state
    private boolean isPlayerXTurn = true;
    private boolean vsComputer; // Flag to indicate if the game is against the computer
    // Assuming ComputerPlayer is a class that implements the logic for the computer player
    private ComputerPlayer computer = new ComputerPlayer();

    public GameBoard(boolean vsComputer) {  // Constructor to initialize the game board
        this.vsComputer = vsComputer;
    setTitle("Tic Tac Toe");
    setUndecorated(true); // Remove window borders and title bar
    setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize to full screen
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new GridLayout(3, 3));

    initializeBoard();
    setVisible(true);
    }

    private void initializeBoard() {    // Method to initialize the game board
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';
            buttons[i] = new JButton("");   // Create a new button for each cell
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));   // Set the font for the buttons
            final int index = i;
            buttons[i].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    handleMove(index);
                }
            });
            add(buttons[i]);    // Add the button to the game board
        }
    }

    // Change checkWin to return the winning combination indices, or null if no win
    private int[] checkWin(char playerSymbol) {
        int[][] winConditions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // Rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // Columns
            {0, 4, 8}, {2, 4, 6}             // Diagonals
        };

        for (int[] condition : winConditions) {
            if (board[condition[0]] == playerSymbol &&
                board[condition[1]] == playerSymbol &&
                board[condition[2]] == playerSymbol) {
                return condition; // Return winning indices
            }
        }
        return null;
    }

    private void handleMove(int index) {        // Method to handle a player's move
        if (board[index] == ' ') {
            board[index] = isPlayerXTurn ? 'X' : 'O';   // Update the board with the player's symbol
            buttons[index].setText(String.valueOf(board[index]));   // Update the button text with the player's symbol
            buttons[index].setEnabled(false);

            char currentPlayer = board[index];  // Get the current player's symbol

            int[] winCombo = checkWin(currentPlayer);
            if (winCombo != null) { // If there's a win
                // Highlight the winning buttons
                for (int i : winCombo) {
                    buttons[i].setBackground(Color.YELLOW);
                }
                int response = JOptionPane.showConfirmDialog(this,
                        "Player " + currentPlayer + " wins! Play again?",
                        "Game Over",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {   // If the player wants to play again
                    resetGame();
                } else {
                    dispose();
                }
                return;
            } else if (checkDraw()) {   // Check if the game is a draw  
                int response = JOptionPane.showConfirmDialog(this,  
                        "It's a draw! Play again?", 
                        "Game Over",
                        JOptionPane.YES_NO_OPTION); 
                if (response == JOptionPane.YES_OPTION) {   // If the player wants to play again
                    resetGame();
                } else {
                    dispose();
                }
                return;
            }

            isPlayerXTurn = !isPlayerXTurn; // Switch turns between players

            // Computer's turn
            if (vsComputer && !isPlayerXTurn) { // If it's the computer's turn
                SwingUtilities.invokeLater(() -> {
                    try {   // Simulate a delay for the computer's move
                        Thread.sleep(300);
                    } catch (InterruptedException e) {  
                        e.printStackTrace();
                    }

                    int move = computer.getMove(board);
                    if (move != -1) {
                        handleMove(move);
                    }
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "That spot is already taken!", "Invalid Move", JOptionPane.WARNING_MESSAGE);    // Show a warning if the spot is already taken
        }
    }

    private boolean checkDraw() {// Method to check if the game is a draw
        for (char c : board) {
            if (c == ' ') return false;
        }
        return true;
    }

    private void resetGame() {// Method to reset the game board for a new game
        for (int i = 0; i < 9; i++) {
            board[i] = ' ';     // Reset the board state
            buttons[i].setText("");     // Clear the button text
            buttons[i].setEnabled(true);
            buttons[i].setBackground(null); // Reset background color
        }
        isPlayerXTurn = true;       // Reset the turn to player X
    }
}

import javax.swing.*;

public class TicTacToe {    // Main class to start the Tic Tac Toe game
    public static void main(String[] args) {    // Main method to launch the game
        String[] options = {"Player vs Player", "Player vs Computer"};  // Options for game modes
        int choice = JOptionPane.showOptionDialog(  // Show a dialog to choose the game mode
            null,
            "Choose game mode:",
            "Tic Tac Toe",
            JOptionPane.DEFAULT_OPTION,   // Default option type
            JOptionPane.QUESTION_MESSAGE,// Message type
            null,
            options,
            options[0]
        );

        boolean vsComputer = (choice == 1); // true if PvC mode
        SwingUtilities.invokeLater(() -> new GameBoard(vsComputer));// Create and show the game board
    }
}

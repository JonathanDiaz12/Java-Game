    import java.util.ArrayList;
    import java.util.Random;

    public class ComputerPlayer {   // Class to handle the computer player's moves
        private Random rand = new Random(); // Random number generator for the computer's moves

        public int getMove(char[] board) {  // Method to get the computer's move
            ArrayList<Integer> available = new ArrayList<>();   // List to hold available moves
            for (int i = 0; i < board.length; i++) {    // Iterate through the board
                if (board[i] == ' ') {  // If the cell is empty, add it to the available moves
                    available.add(i);// Add the index of the empty cell to the list
                }
            }

            if (available.size() > 0) { // If there are available moves
                int choice = rand.nextInt(available.size());    // Randomly select one of the available moves
                return available.get(choice);   // Return the selected move
            }

            return -1; // No valid moves
        }
    }

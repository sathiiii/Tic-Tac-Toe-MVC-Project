/**
 * @author Sathira Silva (E/17/331)
 * 
 * The following GUI program is a Tic-Tac-Toe game designed for two players. The program is built up based on the MVC architecture.
 * Model:       @see TicTacToeModel.java
 * View:        @see TicTacToeView.java
 * Controller:  @see TicTacToeController.java
 * 
 * The default grid size is 3 and can be customized to any other size in range from 3 to 50 as well.
 * The first player starts first and the players should move in alternative turns just like in a normal Tic-Tac-Toe game.
 * After a game over, the user will be notified the winner / tie and will be prompted whether to start a new game or to exit the game.
 */

import javax.swing.JFrame;

public class TicTacToe {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Tic-Tac-Toe Game");
        TicTacToeModel model = new TicTacToeModel();
        TicTacToeView gui = new TicTacToeView(model);
        TicTacToeController controller = new TicTacToeController(gui, model);
        gui.addEventListener(controller);
        frame.setContentPane(gui);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}

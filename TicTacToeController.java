/*
    Copyright (C) 2020, Sathira Silva (E/17/331)

    Following is the base class of the controller for the Tic-Tac-Toe game
*/

import java.awt.*;
import javax.swing.JButton;

public class TicTacToeController {
    // Referrence to the view object
    private TicTacToeView view;
    // Referrence to the model object
    private TicTacToeModel model;
    /**
     * @param magicNumber : Magic number for the nxn magic square
     * @param nMoves : Number of total moves in the current game
     * @param check : The value of the current game to be checked for a win based on the current player
     * @param n : The grid size
     * @param row : The row index of the current pick
     * @param col : The column index of the current pick
     */
    private int magicNumber, nMoves, check, n, row, col;

    public TicTacToeController(TicTacToeView view, TicTacToeModel model) {
        n = model.getSize();
        magicNumber = n * (n * n + 1) / 2;
        this.view = view;
        this.model = model;
    }

    // Method to notify the controller when a player picked a cell
	public void notifyPlayerMoved(int id) {
        nMoves++;
        // Find the row and the column corresponding to the cell that's been clicked
        // (id = N * row + col)
        row = id / n;
        col = id % n;
        JButton clicked = view.getButton(id);
        // If the current player is player 1
        if ((nMoves & 1) == 1) {
            check = magicNumber;
            clicked.setBackground(Color.RED);
            clicked.setText("1");
            // Increment the scores
            model.updateScore(row, row, col, 1);
            model.updateScore(n + col, row, col, 1);
            if (row == col) model.updateScore(2 * n, row, col, 1);
            if (row + col == n - 1) model.updateScore(2 * n + 1, row, col, 1);
        }
        // If the current player is player 2
        else {
            check = -magicNumber;
            clicked.setBackground(Color.GREEN);
            clicked.setText("2");
            // Increment the scores
            model.updateScore(row, row, col, -1);
            model.updateScore(n + col, row, col, -1);
            if (row == col) model.updateScore(2 * n, row, col, -1);
            if (row + col == n - 1) model.updateScore(2 * n + 1, row, col, -1);
        }
	}

    // Method to check whether the current game state is a game over
	public boolean isGameOver() {
		return model.getScore(row) == check || model.getScore(n + col) == check || model.getScore(2 * n) == check || model.getScore(2 * n + 1) == check;
	}

    // Method to reset all of the model, view and the controller itself
	public void resetAll() {
        nMoves = 0;
        view.resetView();
        model.reset();
    }
    
    // Method to get the number of moves made in the current game at a particular time
    public int getMoves() {
        return nMoves;
    }
}

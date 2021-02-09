/*
    Copyright (C) 2020, Sathira Silva (E/17/331)

    Following is the base class of the GUI View for the Tic-Tac-Toe game
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class TicTacToeView extends JPanel implements ActionListener {
    private static final long serialVersionUID = 1L;

    private final int WIDTH = 600, HEIGHT = 600;

    // Referrence to the model object
    private TicTacToeModel model;
    // Referrence to the controller object
    private TicTacToeController controller;
    // JButton array to allocate N * N buttons for the Tic-Tac-Toe grid
    private JButton[] buttons;
    // The dialogbox to display options
    private JOptionPane dialog = new JOptionPane();
    // Grid size
    private int n;

    // Method to initiate the game parameters and the button grid
    private void init() {
        // Create a new spinner JComponent object
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(3, 3, 50, 1));
        // Prompt the user for the grid size
        int option = JOptionPane.showOptionDialog(null, spinner, "Enter the grid size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, null);
        if (option == JOptionPane.CANCEL_OPTION) System.exit(0);
        else model.setSize((int) spinner.getValue());
        model.init();
        n = model.getSize();
        setLayout(new GridLayout(n, n));
        // Allocate the JButton array
        buttons = new JButton[n * n];
        // Iterate through the button array to set their attributes
        for (int i = 0; i < n * n; i++) {
            // Allocate a new button
            buttons[i] = new JButton();
            buttons[i].setForeground(Color.RED);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 45 * 3 / n));
            buttons[i].setName(String.valueOf(i));
            buttons[i].addActionListener(this);
            // Add mouse hover listeners
            buttons[i].addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    if (button.isEnabled())
                        button.setBackground((controller.getMoves() & 1) == 0 ? Color.RED : Color.GREEN);
                }
            
                public void mouseExited(MouseEvent e) {
                    JButton button = (JButton) e.getSource();
                    if (button.isEnabled())
                        button.setBackground(Color.WHITE);
                }
            });
            // Add the button to the view object
            add(buttons[i]);
        }
    }

    public TicTacToeView() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    public TicTacToeView(TicTacToeModel model) {
        this();
        this.model = model;
        init();
    }

    // Set the controller for the game
    public void addEventListener(TicTacToeController controller) {
        this.controller = controller;
        this.controller.resetAll();
    }

    public void resetView() {
        for (int i = 0; i < n * n; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(Color.WHITE);
            buttons[i].setEnabled(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get a referrence object to the clicked button
        JButton clicked = (JButton) e.getSource();
        // Disable the clicked button so that a player cannot play that cell again in the current game
        clicked.setEnabled(false);
        // Find the index of the clicked button in the button array
        int id = Integer.parseInt(clicked.getName());
        controller.notifyPlayerMoved(id);
        int dialogRes = -1;
        // Check for a win (Check the row, column and the diagonal sums)
        if (controller.isGameOver())
            dialogRes = JOptionPane.showConfirmDialog(dialog, "Congratulations! Player " + ((controller.getMoves() & 1) == 1 ? 1 : 2) + " won the game.\n Do you want to play another game?", "Game Over", JOptionPane.YES_NO_OPTION);
        // Check for a tie
        else if (dialogRes == -1 && controller.getMoves() == n * n)
            dialogRes = JOptionPane.showConfirmDialog(dialog, "You Tied!\n Do you want to play another game?", "Game Over", JOptionPane.YES_NO_OPTION);
        // If the game is over, reset the game to start a new game when the user has pressed the Yes option in the dialogbox
        if (dialogRes == JOptionPane.YES_OPTION) controller.resetAll();
        // Exit if the user wanted to quit the game
        else if (dialogRes == JOptionPane.NO_OPTION) System.exit(0);
    }

    // Method to get the button object in the buttons array indexed by id
    public JButton getButton(int id) {
        return buttons[id];
    }
}
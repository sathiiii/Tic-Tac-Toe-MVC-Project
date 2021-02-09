/*
    Copyright (C) 2020, Sathira Silva (E/17/331)

    Following is the base class of the model for the Tic-Tac-Toe game
*/

public class TicTacToeModel {
    private int n;
    // The array to store the sum of the scores in rows, columns, main diagonal and the off diagonal respectively.
    // A score in the (x, y) cell of the Tic-Tac-Toe grid is the value of the magicSquare at (x, y) position.
    // When the player 1 moves to the (x, y) cell, the sums of the xth row, yth column and diagonals (if (x, y) is a main / off diagonal element) are incremented by the 
    // value of the magic square at (x, y). But when player 2 moves, those are decremented by the negative value of the magicSquare at (x, y) to differentiate the winnings
    // of player 1 and player 2.
    // Whenever an entry in the scores array hits the magicValue (for example, 15 for the 3x3 case) player 1 wins.
    // Whenever an entry in the scores array hits -magicValue player 2 wins.
    // (Property of the magic square: All rows, columns and diagonals sum upto the same magic number)
    private int[] scores;
    // A nxn Magic square is the key to identify a win in O(1) for a Tic-Tac-Toe grid of size n.
    private int[][] magicSquare;

    // Method to generate a magic square for odd grid sizes
    private void generateOddOrder(int[][] magicSquare) {
        int N = magicSquare.length;
        int row = N - 1;
	    int col = N / 2;
	    magicSquare[row][col] = 1;
		for(int i = 2; i <= N * N; i++){
			if(magicSquare[(row + 1) % N][(col + 1) % N] == 0){
				row = (row + 1) % N;
				col = (col + 1) % N;
			}else{
				row = (row - 1 + N) % N;
			}
			magicSquare[row][col] = i;
		}
    }

    // Method to generate a magic square for even grid sizes (but not divisible by 4)
    private void generateSinglyEvenOrder() {
        int k = (n - 2) / 4, half = n / 2;;
        int temp;
        int[] swapCol = new int[n];
        int index = 0;
        int[][] miniMagic =  new int[half][half];
        generateOddOrder(miniMagic);
        for (int i = 0; i < half; i++)
            for (int j = 0; j < half; j++){
                magicSquare[i][j] = miniMagic[i][j];
                magicSquare[i + half][j + half] = miniMagic[i][j] + half * half;
                magicSquare[i][j + half] = miniMagic[i][j] + 2 * half * half;
                magicSquare[i + half][j] = miniMagic[i][j] + 3 * half * half;
            }
        for (int i = 1; i <= k; i++)
            swapCol[index++] = i;
        for (int i = n - k + 2; i <= n; i++)
            swapCol[index++] = i;
        for (int i = 1; i <= half; i++)
            for (int j=1; j<=index; j++){
                temp = magicSquare[i - 1][swapCol[j - 1] - 1];
                magicSquare[i - 1][swapCol[j - 1] - 1] = magicSquare[i + half - 1][swapCol[j - 1] - 1];
                magicSquare[i + half - 1][swapCol[j - 1] - 1] = temp;
            }
        temp=magicSquare[k][0]; 
        magicSquare[k][0] = magicSquare[k + half][0]; 
        magicSquare[k + half][0] = temp;
        temp = magicSquare[k + half][k]; 
        magicSquare[k + half][k] = magicSquare[k][k]; 
        magicSquare[k][k] = temp;
    }

    // Method to generate a magic square for grid sizes that is divisible by 4
    private void generateDoublyEvenOrder() {
        int quater = n / 4, count = 1, invCount = n * n;
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                if(j >= quater && j < n - quater){
                    if(i >= quater && i < n - quater)
                        magicSquare[i][j] = count;
                    else 
                        magicSquare[i][j] = invCount;

                }
                else if(i < quater || i >= n - quater)
                    magicSquare[i][j]=count;
                else
                    magicSquare[i][j] = invCount;
                count++;
                invCount--;
            }
        }
    }

    // Method to get the grid size
    public int getSize() {
        return n;
    }

    // Method to set the grid size
    public void setSize(int n) {
        this.n = n;
    }

    // Method to get a score in the scores array at index id
    public int getScore(int id) {
        return scores[id];
    }

    // Method to update an entry in the scores array given the index, row, column and the current player
    public void updateScore(int id, int row, int col, int player) {
        scores[id] += magicSquare[row][col] * player;
    }

    // Method to reset the model parameters
    public void reset() {
        scores = new int[2 * n + 2];
    }

    // Method to iniitalize the model parameters
    public void init() {
        magicSquare = new int[n][n];
        if ((n & 1) == 1) generateOddOrder(magicSquare);
        else if (n % 4 == 0) generateDoublyEvenOrder();
        else generateSinglyEvenOrder();
    }
}

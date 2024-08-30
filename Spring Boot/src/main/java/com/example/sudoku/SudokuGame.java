package com.example.sudoku;

import java.util.Random;

public class SudokuGame {
    private int[][] board = new int[9][9];
    private int[][] solution = new int[9][9];
    private Random random = new Random();

    public void generateSudoku() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                board[i][j] = 0;
                solution[i][j] = 0;
            }
        }

        fillDiagonal();
        fillRemaining(0, 3);
        for (int i = 0; i < 9; i++) {
            System.arraycopy(board[i], 0, solution[i], 0, 9);
        }
        removeDigits();
    }

    private void fillDiagonal() {
        for (int i = 0; i < 9; i = i + 3) {
            fillBox(i, i);
        }
    }

    private void fillBox(int row, int col) {
        int num;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                do {
                    num = random.nextInt(9) + 1;
                } while (!isValidInBox(row, col, num));
                board[row + i][col + j] = num;
            }
        }
    }

    private boolean isValidInBox(int rowStart, int colStart, int num) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[rowStart + i][colStart + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean fillRemaining(int i, int j) {
        if (j >= 9 && i < 8) {
            i = i + 1;
            j = 0;
        }
        if (i >= 9 && j >= 9) {
            return true;
        }
        if (i < 3) {
            if (j < 3) {
                j = 3;
            }
        } else if (i < 6) {
            if (j == (i / 3) * 3) {
                j = j + 3;
            }
        } else {
            if (j == 6) {
                i = i + 1;
                j = 0;
                if (i >= 9) {
                    return true;
                }
            }
        }

        for (int num = 1; num <= 9; num++) {
            if (isValidMove(i, j, num)) {
                board[i][j] = num;
                if (fillRemaining(i, j + 1)) {
                    return true;
                }
                board[i][j] = 0;
            }
        }
        return false;
    }

    private void removeDigits() {
        int count = 40; // Number of cells to remove
        while (count != 0) {
            int cellId = random.nextInt(81);
            int i = cellId / 9;
            int j = cellId % 9;
            if (board[i][j] != 0) {
                count--;
                board[i][j] = 0;
            }
        }
    }

    public boolean isValidMove(int row, int col, int num) {
        // Check row
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num && i != col) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num && i != row) {
                return false;
            }
        }

        // Check box
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == num && (i != row || j != col)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setCell(int row, int col, int value) {
        board[row][col] = value;
    }

    public int getSolutionCell(int row, int col) {
        return solution[row][col];
    }
}
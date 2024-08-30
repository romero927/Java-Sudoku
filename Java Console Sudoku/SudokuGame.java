// Save this file as SudokuGame.java
public class SudokuGame {
    private int[][] board = new int[9][9];
    private int[][] solution = new int[9][9];
    private java.util.Random random = new java.util.Random();
    private java.util.Scanner scanner = new java.util.Scanner(System.in);

    public static void main(String[] args) {
        SudokuGame game = new SudokuGame();
        game.play();
    }

    public void play() {
        generateSudoku();
        while (!isSolved()) {
            printBoard();
            makeMove();
        }
        System.out.println("Congratulations! You've solved the Sudoku puzzle!");
    }

    private void generateSudoku() {
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

    private boolean isValidMove(int row, int col, int num) {
        // Check row
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == num) {
                return false;
            }
        }

        // Check column
        for (int i = 0; i < 9; i++) {
            if (board[i][col] == num) {
                return false;
            }
        }

        // Check box
        int boxRow = row - row % 3;
        int boxCol = col - col % 3;
        for (int i = boxRow; i < boxRow + 3; i++) {
            for (int j = boxCol; j < boxCol + 3; j++) {
                if (board[i][j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean isSolved() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    private void printBoard() {
        System.out.println("---------------------");
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (j % 3 == 0) System.out.print("|");
                System.out.print(board[i][j] == 0 ? " " : Integer.toString(board[i][j]));
                System.out.print(" ");
            }
            System.out.println("|");
            if (i % 3 == 2) System.out.println("---------------------");
        }
    }

    private void makeMove() {
        int row, col, value;
        do {
            System.out.print("Enter row (1-9): ");
            row = scanner.nextInt() - 1;
            System.out.print("Enter column (1-9): ");
            col = scanner.nextInt() - 1;
            System.out.print("Enter value (1-9): ");
            value = scanner.nextInt();
        } while (!isValidInput(row, col, value));

        if (board[row][col] == 0) {
            if (solution[row][col] == value) {
                board[row][col] = value;
                System.out.println("Correct move!");
            } else {
                System.out.println("Incorrect value. Try again.");
            }
        } else {
            System.out.println("This cell is already filled. Choose an empty cell.");
        }
    }

    private boolean isValidInput(int row, int col, int value) {
        if (row < 0 || row >= 9 || col < 0 || col >= 9 || value < 1 || value > 9) {
            System.out.println("Invalid input. Row and column should be between 1 and 9, and value should be between 1 and 9.");
            return false;
        }
        return true;
    }
}

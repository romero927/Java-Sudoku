import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SudokuGameGUI extends JFrame {
    private int[][] board = new int[9][9];
    private int[][] solution = new int[9][9];
    private JTextField[][] cells = new JTextField[9][9];
    private Random random = new Random();
    private JLabel timerLabel;
    private Timer timer;
    private int secondsElapsed;

    public SudokuGameGUI() {
        setTitle("Sudoku");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel gamePanel = new JPanel(new GridLayout(3, 3, 3, 3));
        gamePanel.setBorder(new EmptyBorder(3, 3, 3, 3));
        gamePanel.setBackground(Color.BLACK);

        for (int boxRow = 0; boxRow < 3; boxRow++) {
            for (int boxCol = 0; boxCol < 3; boxCol++) {
                JPanel box = new JPanel(new GridLayout(3, 3, 1, 1));
                box.setBackground(Color.BLACK);
                gamePanel.add(box);

                for (int cellRow = 0; cellRow < 3; cellRow++) {
                    for (int cellCol = 0; cellCol < 3; cellCol++) {
                        int row = boxRow * 3 + cellRow;
                        int col = boxCol * 3 + cellCol;
                        cells[row][col] = createTextField(row, col);
                        box.add(cells[row][col]);
                    }
                }
            }
        }
        add(gamePanel, BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new FlowLayout());
        JButton newGameButton = new JButton("New Game");
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateSudoku();
                updateGUI();
                resetTimer();
            }
        });
        controlPanel.add(newGameButton);

        timerLabel = new JLabel("Time: 00:00");
        controlPanel.add(timerLabel);

        add(controlPanel, BorderLayout.SOUTH);

        initializeTimer();
        generateSudoku();
        updateGUI();
        startTimer();

        setSize(500, 550);
        setVisible(true);
    }

    private void initializeTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsElapsed++;
                updateTimerLabel();
            }
        });
    }

    private void startTimer() {
        timer.start();
    }

    private void stopTimer() {
        timer.stop();
    }

    private void resetTimer() {
        stopTimer();
        secondsElapsed = 0;
        updateTimerLabel();
        startTimer();
    }

    private void updateTimerLabel() {
        int minutes = secondsElapsed / 60;
        int seconds = secondsElapsed % 60;
        timerLabel.setText(String.format("Time: %02d:%02d", minutes, seconds));
    }

    private JTextField createTextField(final int row, final int col) {
        JTextField textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font("Arial", Font.BOLD, 20));

        ((AbstractDocument)textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String newValue = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                if ((fb.getDocument().getLength() + text.length() - length) <= 1 && text.matches("\\d*")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });

        textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validateInput(row, col); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validateInput(row, col); }
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validateInput(row, col); }
        });

        return textField;
    }

    private void validateInput(int row, int col) {
        String input = cells[row][col].getText();
        if (!input.isEmpty()) {
            try {
                int value = Integer.parseInt(input);
                if (value < 1 || value > 9 || !isValidMove(row, col, value)) {
                    setInvalidCell(row, col, "Invalid input: " + (value < 1 || value > 9 ? "Number must be between 1 and 9" : "This number already exists in the row, column, or 3x3 box"));
                } else if (value == solution[row][col]) {
                    setCellValid(row, col);
                    cells[row][col].setForeground(Color.BLUE);
                    cells[row][col].setEditable(false);
                    board[row][col] = value;
                    if (isSolved()) {
                        stopTimer();
                        JOptionPane.showMessageDialog(this, "Congratulations! You've solved the Sudoku puzzle!\nYour time: " + timerLabel.getText());
                    }
                } else {
                    setCellValid(row, col);
                    cells[row][col].setForeground(Color.BLACK);
                }
            } catch (NumberFormatException e) {
                setInvalidCell(row, col, "Please enter a number");
            }
        } else {
            setCellValid(row, col);
        }
    }

    private void setInvalidCell(int row, int col, String message) {
        cells[row][col].setForeground(Color.RED);
        cells[row][col].setBackground(Color.YELLOW);
        cells[row][col].setToolTipText(message);
        cells[row][col].setBorder(BorderFactory.createLineBorder(Color.RED, 2));
    }

    private void setCellValid(int row, int col) {
        cells[row][col].setForeground(Color.BLACK);
        cells[row][col].setBackground(getDefaultCellColor(row, col));
        cells[row][col].setToolTipText(null);
        cells[row][col].setBorder(UIManager.getBorder("TextField.border"));
    }

    private Color getDefaultCellColor(int row, int col) {
        int boxRow = row / 3;
        int boxCol = col / 3;
        return (boxRow + boxCol) % 2 == 0 ? new Color(240, 240, 240) : Color.WHITE;
    }

    private void generateSudoku() {
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

    private boolean isValidMove(int row, int col, int num) {
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

    private void updateGUI() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != 0) {
                    cells[i][j].setText(String.valueOf(board[i][j]));
                    cells[i][j].setEditable(false);
                    cells[i][j].setBackground(new Color(220, 220, 220));
                } else {
                    cells[i][j].setText("");
                    cells[i][j].setEditable(true);
                    cells[i][j].setBackground(getDefaultCellColor(i, j));
                    cells[i][j].setForeground(Color.BLACK);
                    cells[i][j].setToolTipText(null);
                    cells[i][j].setBorder(UIManager.getBorder("TextField.border"));
                }
            }
        }
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new SudokuGameGUI();
            }
        });
    }
}

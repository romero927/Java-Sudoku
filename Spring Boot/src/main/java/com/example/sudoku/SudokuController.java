package com.example.sudoku;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class SudokuController {

    private SudokuGame game = new SudokuGame();
    private long startTime;

    @GetMapping("/")
    public String index(Model model) {
        game.generateSudoku();
        startTime = System.currentTimeMillis();
        model.addAttribute("board", game.getBoard());
        return "sudoku";
    }

    @PostMapping("/move")
    @ResponseBody
    public String makeMove(@RequestParam int row, @RequestParam int col, @RequestParam int value) {
        if (game.isValidMove(row, col, value)) {
            game.setCell(row, col, value);
            if (game.isSolved()) {
                long endTime = System.currentTimeMillis();
                long timeElapsed = (endTime - startTime) / 1000;
                return "Congratulations! You've solved the puzzle in " + timeElapsed + " seconds!";
            }
            return "Valid move";
        }
        return "Invalid move";
    }

    @GetMapping("/new-game")
    public String newGame(Model model) {
        game.generateSudoku();
        startTime = System.currentTimeMillis();
        model.addAttribute("board", game.getBoard());
        return "sudoku";
    }
}
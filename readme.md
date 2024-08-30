# Sudoku Game Implementations

This project contains three different implementations of a Sudoku game:
1. Console-based version
2. GUI version using Java Swing
3. Web-based version using Spring Boot

All versions, including this README, were generated by Claude, an AI assistant, under the direction of Kyle Romero (https://kgromero.com).

## Project Direction
This project was conceived and directed by Kyle Romero. For more information about Kyle and his work, visit https://kgromero.com.

## Common Features Across All Versions
- Randomly generated Sudoku puzzles
- Real-time input validation
- Timer to track gameplay duration
- Option to start a new game
- Congratulatory message upon successful completion

## 1. Console-based Sudoku

### Description
A text-based Sudoku game that runs in the command line interface.

### Requirements
- Java Development Kit (JDK) 8 or higher

### Setup and Running
1. Save the code in `SudokuGame.java`
2. Compile: `javac SudokuGame.java`
3. Run: `java SudokuGame`

### How to Play
- Enter row (1-9), column (1-9), and value (1-9) when prompted
- Type '0' for row to start a new game

## 2. GUI Sudoku using Java Swing

### Description
A graphical Sudoku game with a user-friendly interface using Java Swing.

### Requirements
- Java Development Kit (JDK) 8 or higher

### Setup and Running
1. Save the code in `SudokuGameGUI.java`
2. Compile: `javac SudokuGameGUI.java`
3. Run: `java SudokuGameGUI`

### How to Play
- Click on a cell and type a number
- Use the "New Game" button to start a fresh puzzle

## 3. Web-based Sudoku using Spring Boot

### Description
A web application version of the Sudoku game using Spring Boot framework.

### Requirements
- Java Development Kit (JDK) 8 or higher
- Maven

### Setup and Running
1. Ensure the project structure is set up as described in the Spring Boot section
2. Build the project: `mvn clean install`
3. Run the application: `mvn spring-boot:run`
4. Open a web browser and go to `http://localhost:8080`

### How to Play
- Click on a cell and type a number
- Use the "New Game" button to start a fresh puzzle

## Project Structure

### Console Version
- Single file: `SudokuGame.java`

### GUI Version
- Single file: `SudokuGameGUI.java`

### Spring Boot Version
```
sudoku-spring-boot/
│
├── pom.xml
│
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── example/
        │           └── sudoku/
        │               ├── SudokuApplication.java
        │               ├── SudokuGame.java
        │               └── SudokuController.java
        │
        └── resources/
            └── templates/
                └── sudoku.html
```

## Customization
Each version can be customized by modifying the respective source files:
- Adjust the number of pre-filled cells
- Change the puzzle generation algorithm
- Modify the UI/UX design (for GUI and Web versions)

## Acknowledgments
These Sudoku game implementations, including all code and documentation, were entirely generated by Claude, an AI assistant created by Anthropic, under the direction of Kyle Romero. The project demonstrates Claude's ability to design, implement, and document various versions of a functional application based on human guidance and requirements.

## License
This project is open source and available under the MIT License.

## Contributing
As this project was AI-generated under human direction, contributions that build upon or improve the existing code are welcome. Please feel free to fork the repository and submit pull requests with any enhancements.

## Disclaimer
While every effort has been made to ensure the correctness and efficiency of this AI-generated code, it may not be optimized for all use cases. Users are encouraged to review and test the code thoroughly before use in any critical applications.

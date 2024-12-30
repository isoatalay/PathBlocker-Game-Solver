# Path Blocker

## Overview
This project is designed to explore and implement **uninformed search algorithms** in a grid-based puzzle environment. The primary focus is on understanding and applying algorithms like Iterative Deepening Depth-First Search (IDDFS) while solving the "Path Blocker" puzzle.

In the game, the player (`P`) must navigate through a grid to reach the goal (`G`), avoiding walls and following specific movement rules.

---

## Features
- Implements **Iterative Deepening Depth-First Search (IDDFS)** for optimal pathfinding.
- Supports multiple custom-designed levels in text format.
- Provides visual outputs of the solution process.
- Demonstrates efficient state management to optimize memory usage.

---

## Goals
1. Understand uninformed search algorithms through practical implementation.
2. Learn how to manage and traverse state spaces efficiently.
3. Visualize the process of solving grid-based puzzles using search algorithms.

---

## Folder Structure
- **`src/pathblocker`**: Contains the Java source files.
  - `FileExecuter.java`: Reads and parses level files.
  - `PathBlockerState.java`: Manages game states and player movements.
  - `IterativeMemoryEfficiency.java`: Implements the IDDFS algorithm.
  - Other supporting files for game logic.
- **`src/pathblocker/levels`**: Contains level files in `.txt` format.
  - Example: `level5.txt` defines a level layout with walls, player, and goal.
- **`output_images`**: Stores screenshots of the solution process (if enabled in the code).

---

## How to Run
1. **Setup**:
   - Ensure you have Java installed (JDK 8+).
   - Clone the repository:
     ```bash
     git clone https://github.com/yourusername/path-blocker.git
     cd path-blocker
     ```

2. **Compile**:
   - Navigate to the `src` folder and compile the Java files:
     ```bash
     javac pathblocker/*.java
     ```

3. **Run**:
   - Execute the main program:
     ```bash
     java pathblocker.PathBlocker
     ```
   - The program will read levels from the `src/pathblocker/levels` folder and display results.

4. **Output**:
   - Visual solutions will be saved in the `output_images` folder (if configured).

---

## Attribution
This project is inspired by the game [Path Blocker](https://www.kongregate.com/games/zabobinozaur/path-blocker) by Zabobinozaur on Kongregate. The code and implementation are adapted for educational purposes to explore search algorithms.

---

## License
This project is licensed under the [MIT License](LICENSE).

---

## Future Work
- Add more uninformed search algorithms (e.g., Breadth-First Search, Depth-First Search).
- Extend to informed search algorithms like A*.
- Improve visualization for the solution process.

package pathblocker;

/*
1) Why you prefer the search algorithm you choose?
        We chose IDDFS because it offers a great balance between memory efficiency and finding the optimal solution.
        While we do sacrifice some time by re-exploring nodes at increasing depth limits, 
    this is a worthwhile trade-off for ensuring that we find the shortest possible path. 
        Additionally, IDDFS can sometimes even outperform DFS in terms of time when the solution is shallow.

2) Can you achieve the optimal result? Why? Why not?
        Yes, the optimal result can be achieved. 
        This is because iterative deepening search (IDS) gradually increases the depth limit, 
    searching deeper levels one by one. At each level, it performs a depth-first search up to the current depth limit. 
        When the optimal solution exists at a certain depth,    
        IDS ensures that it finds this solution without exploring unnecessary deeper nodes. 
        Since the method systematically increases the depth and always checks the shallowest levels first, 
        it guarantees finding the optimal solution if one exists, 
        especially when path cost is uniform (like in unit step problems).
    
3) How you achieved efficiency for keeping the states?
        Efficiency in managing states is achieved by reusing existing state information rather than creating new state objects at each step of the search. 
        Here's how it works:
        While traversing the search tree, instead of generating a completely new state object for each movement, 
    we store only the previous moves (and the length of each move) in a stack.
        This allows us to backtrack easily using an undo() function, which lets us return to previous states as needed.
        By doing this, we keep all operations within a single state until we find the solution, 
    without the overhead of constantly creating new state objects.
        Once the solution is found, we then create separate objects for each step from the solution state, 
    which helps us reconstruct the exact sequence of moves to reach the goal. 
        This approach reduces memory usage and speeds up the search process.

4) If you prefer to use DFS (tree version) then do you need to avoid cycles?
        If we used DFS (tree version), we wouldn't need to avoid cycles explicitly because the game’s mechanics prevent them.
        When the player moves in a direction, walls are effectively created along the path, 
    meaning the player cannot return to the same position and configuration as their parent state. 
        This prevents cycles naturally, so there's no need to track visited states. 

5) What will be the path-cost for this problem?
        The path-cost in this game is the number of steps the player takes to reach the goal. 
        For example, if reaching the goal requires 7 steps, the path-cost is 7. 
        The algorithm ensures the solution found is the one with the least path-cost, 
    meaning the shortest sequence of steps to the goal. 
        The path-cost will vary depending on the complexity of each level, 
    but it always reflects the minimum number of steps needed to solve the puzzle.
 */

import java.util.List;

public class PathBlocker {

    public static void main(String[] args) throws Exception {
        // TODO code application logic here
        FileExecuter files = new FileExecuter("src/pathblocker/levels");
        List<PathBlockerState> levels = files.getLevels();
        System.out.println("");

        boolean saveAsPng = true;
        long total = 0;
        List<PathBlockerState> solutionSequence = null;
        for (PathBlockerState level : levels) {
            
            long startTime = System.nanoTime();
            IterativeMemoryEfficiency a = new IterativeMemoryEfficiency(Integer.MAX_VALUE);
            solutionSequence = a.solve(level);
            long endTime = System.nanoTime();
            
            total += endTime - startTime;
            System.out.println("For " + level.levelname + " " + "Execution time : " + (endTime - startTime) / 1e6 + " milliseconds");
            
            if (saveAsPng) {
                ImageConverter images = new ImageConverter(solutionSequence);
                images.saveAsPng();
            }
        }

        System.out.println("\nTotal time for all levels " + total / 1e6 + " millisecons");

    }

}

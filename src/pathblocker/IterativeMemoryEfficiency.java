package pathblocker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IterativeMemoryEfficiency {

    public int depthlimit;

    public IterativeMemoryEfficiency(int depthlimit) {
        this.depthlimit = depthlimit;
    }

    public List<PathBlockerState> solve(PathBlockerState state) throws Exception {
        for (int currentDepthLimit = 0; currentDepthLimit < depthlimit; currentDepthLimit = currentDepthLimit + 1) {
            if (recursive(state, currentDepthLimit, depthlimit)) {
                return createSolutionSequence(state);
            }
        }

        throw new Exception("No solution to be found");
    }

    public boolean recursive(PathBlockerState state, int currentDepthLimit, int depthLimit) {
        if (state.isGoal()) {
            return true;
        }

        if (currentDepthLimit < depthLimit) {
            for (int i = 0; i < state.possibleMovements().size(); i++) {

                state.doAction(state.possibleMovements().get(i));
                boolean goalFound = recursive(state, (currentDepthLimit + 1), depthLimit);
                if (goalFound) {
                    return true;
                }

                state.undoAction();
            }
        }

        return false;
    }

    public List<PathBlockerState> createSolutionSequence(PathBlockerState solution) {
        List<PathBlockerState> solutionSequence = new ArrayList<>();
        do {
            solutionSequence.add(new PathBlockerState(solution));
            solution.undoAction();

        } while (!solution.isRoot());

        solutionSequence.add(new PathBlockerState(solution));

        Collections.reverse(solutionSequence);
        return solutionSequence;
    }
}

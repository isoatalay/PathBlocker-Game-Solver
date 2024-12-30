package pathblocker;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class PathBlockerState extends State implements Cloneable {

    private Stack<int[]> movementOrder = new Stack<>();  // when undo() called  pop keeps the direction of the last movement 
    private Stack<Integer> countBlock = new Stack<>(); // when undo() called pop keeps how many block player come back 
    public String levelname;
    private final Features[][] world;
    private final int[][] movements = {{0, -1}, {-1, 0}, {0, 1}, {1, 0}}; // left , up , right, down in order
    private int PlayerPositionX;
    private int PlayerPositionY;
    private int GoalPositonX;
    private int GoalPositonY;

    public PathBlockerState(String map, String levelName) {

        this.levelname = levelName.substring(0, 5) + " " + levelName.substring(5);
        int size = (map.split("\n")).length;
        this.world = new Features[size][size];
        createBoard(map);

    }

    public PathBlockerState(PathBlockerState state) {

        this.world = new Features[state.world.length][];
        for (int i = 0; i < state.world.length; i++) {
            this.world[i] = state.world[i].clone();
        }
        this.levelname = state.levelname;
        this.PlayerPositionX = state.PlayerPositionX;
        this.PlayerPositionY = state.PlayerPositionY;
        this.GoalPositonX = state.GoalPositonX;
        this.GoalPositonY = state.GoalPositonY;

    }

    public boolean isRoot() {
        return movementOrder.isEmpty();
    }


    public Features[][] getWorld() {
        return this.world;
    }

    

    private void createBoard(String map) {
        boolean hasPlayer = false;
        boolean hasGoal = false;
        String[] a = map.split("\n");
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[i].length(); j++) {
                if (Features.selectFeatures(a[i].charAt(j)) == Features.player) {
                    if (hasPlayer == true) {
                        throw new IllegalArgumentException("2 Player cannot be exist");
                    } else {
                        world[i][j] = Features.player;
                        PlayerPositionX = i;
                        PlayerPositionY = j;
                        hasPlayer = true;
                    }

                } else if (Features.selectFeatures(a[i].charAt(j)) == Features.goal) {
                    if (hasGoal == true) {
                        throw new IllegalArgumentException("2 Player cannot be exist");
                    } else {
                        world[i][j] = Features.goal;
                        GoalPositonX = i;
                        GoalPositonY = j;
                        hasGoal = true;
                    }
                } else {
                    world[i][j] = Features.selectFeatures(a[i].charAt(j));
                }
            }
        }
    }

    @Override
    public PathBlockerState clone() throws CloneNotSupportedException {
        return new PathBlockerState(this);  // Use the deep copy constructor
    }

    @Override
    public void doAction(int[] movement) {
        int count = 0;
        do {
            if (isGoal()) {
                break;
            } else {
                int newPositionX = movement[0] + this.PlayerPositionX;
                int newPositionY = movement[1] + this.PlayerPositionY;
                this.world[newPositionX][newPositionY] = Features.player;
                this.world[this.PlayerPositionX][this.PlayerPositionY] = Features.wall;
                this.PlayerPositionX = newPositionX;
                this.PlayerPositionY = newPositionY;
                count++;
            }

        } while (this.isMovable(movement));
        countBlock.push(count);
        movementOrder.push(movement);

    }

    @Override
    public void undoAction() {

        if (movementOrder.size() > 0) {
            int[] lastMovement = movementOrder.pop();
            int count = countBlock.pop();
            for (int i = 0; i < count; i++) {
                if (isGoal()) {
                    int newPositionX = +this.PlayerPositionX - lastMovement[0];
                    int newPositionY = +this.PlayerPositionY - lastMovement[1];
                    this.world[newPositionX][newPositionY] = Features.player;
                    this.world[this.PlayerPositionX][this.PlayerPositionY] = Features.goal;
                    this.PlayerPositionX = newPositionX;
                    this.PlayerPositionY = newPositionY;
                } else {
                    int newPositionX = +this.PlayerPositionX - lastMovement[0];
                    int newPositionY = +this.PlayerPositionY - lastMovement[1];
                    this.world[newPositionX][newPositionY] = Features.player;
                    this.world[this.PlayerPositionX][this.PlayerPositionY] = Features.notWall;
                    this.PlayerPositionX = newPositionX;
                    this.PlayerPositionY = newPositionY;
                }

            }
        } else {
            throw new IllegalStateException("There is no previous state");
        }
    }

    public List<int[]> possibleMovements() {
        List<int[]> possibleMovements = new ArrayList<>();
        for (int[] movement : movements) {
            if (isMovable(movement)) {
                possibleMovements.add(movement);
            }
        }
        return possibleMovements;

    }

    public boolean isMovable(int[] movement) {
        int newX = PlayerPositionX + movement[0];
        int newY = PlayerPositionY + movement[1];
        if (newX >= 0 && newX < world.length && newY >= 0 && newY < world[0].length) {
            if (world[newX][newY] == Features.wall) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        for (Features[] features : world) {
            for (Features feature : features) {
                sb.append(feature + " ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else {
            PathBlockerState p = (PathBlockerState) o;
            return p.world == this.world;
        }
    }

    @Override
    public boolean isGoal() {
        return (PlayerPositionX == GoalPositonX) && (PlayerPositionY == GoalPositonY);
    }

}

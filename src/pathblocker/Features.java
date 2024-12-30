
package pathblocker;


public enum Features {
    wall, notWall, player, goal;

    public static Features selectFeatures(char c) {
        switch (c) {
            case ' ':
                return notWall;
            case '*':
                return wall;
            case 'P':
                return player;
            case 'G':
                return goal;
            default:
                throw new IllegalArgumentException("(" + c + ") " + "is not a feature for board");
        }
    }

    @Override
    public String toString() {
        switch (this) {
            case notWall:
                return " ";
            case wall:
                return "*";
            case player:
                return "P";
            case goal:
                return "G";
            default:
                throw new AssertionError();
        }
    }

}

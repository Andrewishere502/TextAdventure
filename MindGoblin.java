import java.util.Stack;


public class MindGoblin extends Entity {
    private AStar mind;
    private Stack<int[]> path;

    public MindGoblin(int xPos, int yPos) {
        // Using Entity's constructor
        super(
            "Mind Goblin",
            xPos, yPos,
            3,      // Health
            2,      // Strength
            0       // Max inventory size
        );
    }

    public void updateMind(int[] target, int[][] grid) {
        // Set the position of this entity to the source
        int[] source = new int[] {getXPos(), getYPos()};
        mind = new AStar(source, target, grid);
        // Calculate the path to the target
        path = mind.solve();
    }

    public int[] nextMove() {
        // If the path hasn't been calculated yet for some reason,
        // just stay in the same spot
        if (path == null)
            return new int[] {getXPos(), getYPos()};

        return path.pop();
    }
}
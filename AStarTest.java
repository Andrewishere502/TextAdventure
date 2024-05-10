import java.util.Stack;


public class AStarTest {
    // Test the algorithm
    public static void main(String[] args) {
        // Set up the grid
        int width = 8;
        int height = 6;
        int[][] grid = new int[height][width];

        // Add some obsticles
        grid[2][0] = 1;

        int[] source = new int[] {0, 0};
        int[] target = new int[] {4, 4};

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (source[0] == j && source[1] == i)
                    System.out.print("s  ");
                else if (target[0] == j && target[1] == i)
                    System.out.print("T  ");
                else
                    System.out.printf("%d  ", grid[i][j]);
            }
            System.out.println();  // Go to the next line after each row
        }

        // Calculate the optimal route
        AStar astar = new AStar(source, target, grid);
        // Print out the path
        Stack<int[]> path = astar.solve();
        while (!path.isEmpty()) {
            int[] nextTile = path.pop();
            System.out.printf("%d %d%n", nextTile[0], nextTile[1]);
        }
    }
}
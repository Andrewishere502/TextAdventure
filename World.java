import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.Queue;

import java.security.SecureRandom;
import java.util.LinkedList;


public class World {
    // Create random generator
    private SecureRandom random = new SecureRandom();

    // Track which turn the player is on
    private int turn = 0;

    // Store player and other characters
    private Player player;
    private LinkedList<Character> npcs = new LinkedList<Character>();

    // Width and height of the grid
    private final int width;
    private final int height;
    // Store the grid as a 2D array
    private int[][] grid;
    // Table to convert integers to their character representation
    private char[] charTable = {
        '.', // 0, empty tile
        '#', // 1, wall tile
        '~', // 2, water tile
        '*', // 3, gate tile
        '.', // 4
        '.', // 5
        '.', // 6
        '.', // 7
        '&', // 8, goblin tile
        '@' // 9, player tile
    };
    // Store a queue of tiles that were moved onto by something...
    // or someONE :O
    private Queue<Integer> tileQueue = new Queue<Integer>();

    // Public constructor
    public World(int width, int height, int[] playerLocation) {
        this.width = width;
        this.height = height;
        // Generate the grid
        generateGrid();

        spawn(playerLocation[0], playerLocation[1], 9); // 9 is tile ID of player character
        player = new Player(
            playerLocation[0],  // Starting x coordinate
            playerLocation[1]   // Starting y coordinate
            );
    }

    // Populate the grid with more than just empty squares
    private void generateGrid() {
        // Initiate the grid with all 0s, the empty tile square
        grid = new int[height][width];

        // Add a small patch of water somewhere (tile id 3)
        addSquarePatch(width, height, 3, 6, 3, 6, 2);
    }

    // Add a small square patch of some tile into a random spot on the grid
    private void addSquarePatch(int width, int height, int minWidth, int maxWidth, int minHeight, int maxHeight, int tile) {
        // Get random size of patch
        int patchWidth = minWidth + random.nextInt(maxWidth - minWidth);
        int patchHeight = minHeight + random.nextInt(maxHeight - minHeight);

        // Get random starting x and y coordinates
        int startX = random.nextInt(width - patchWidth);
        int startY = random.nextInt(height - patchHeight);

        // Place the tile in the patch area
        for (int i = startY; i < startY + patchHeight; i++) {
            for (int j = startX; j < startX + patchWidth; j++) {
                grid[i][j] = tile;
            }
        }
    }

    // Return true if a tile at some location is passable
    private boolean isPassable(int x, int y) {
        int tile;
        try {
            tile = grid[y][x];
        } catch (IndexOutOfBoundsException e) {
            // The specified tile is not valid in the grid,
            // so the player can't move there.
            return false;
        }
        // impassable tiles:
        //      - 1: wall tile
        //      - 2: water tile
        return tile != 1 && tile != 2;
    }

    // Create a symbol table that stores the movement vector associated
    // with each movement direction.
    public ST<Character,int[]> getValidMoves(int x, int y) {
        ST<Character,int[]> validMoves = new ST<Character,int[]>();

        // Not in left-most column, so player can move west
        if (isPassable(x - 1, y)) {
            validMoves.put(
                'w',        // w for west
                new int[] {-1, 0}     // Move -1 in x axis and 0 in y axis
                );
        }

        // Not in right-most column, so player can move east
        if (isPassable(x + 1, y)) {
            validMoves.put(
                'e',        // e for west
                new int[] {1, 0}     // Move 1 in x axis and 0 in y axis
                );
        }

        // Not in top row, so player can move north
        if (isPassable(x, y - 1)) {
            validMoves.put(
                'n',        // n for west
                new int[] {0, -1}     // Move 0 in x axis and -1 in y axis
                );
        }

        // Not in bottom row, so player can move south
        if (isPassable(x, y + 1)) {
            validMoves.put(
                's',        // w for west
                new int[] {0, 1}     // Move 0 in x axis and 1 in y axis
                );
        }

        return validMoves;
    }

    // Place something on the grid, placing the int that was
    // at that position on the tile queue.
    private void place(int x, int y, int tile) {
        // Add removed tile to the tile queue
        tileQueue.enqueue(grid[y][x]);
        // Place the new tile at the desired location
        grid[y][x] = tile;
    }

    // Replace a tile on the grid from where (hopefully) it was before,
    // and return the old tile.
    private int replace(int x, int y) {
        // Store the old tile value
        int oldTile = grid[y][x];
        // Replace the specified tile with the next thing on the queue
        grid[y][x] = tileQueue.dequeue();
        // Return old tile value
        return oldTile;
    }

    // Allow spawning of an entity at some location
    public void spawn(int x, int y, int tile) {
        // Place the tile at the specified location
        place(x, y, tile);
    }

    // Move something one tile in any direction (NSEW), and return
    // it's new location as coordinates (x, y)
    private int[] move(int x, int y, char direction) {
        // Get the valid moves for a given position
        ST<Character,int[]> validMoves = getValidMoves(x, y);

        // Get movement vector for this movement direction
        int[] vector = validMoves.get(direction); 
        if (vector == null) {  // Direction was invalid and returned a null vector
            // Throw an error for invalid movement
            throw new IllegalArgumentException("Invalid movement direction");
        }

        // Calculate new x and y position
        int newX = x + vector[0];
        int newY = y + vector[1];

        // Remove and store the tile to be moved
        int moveTile = replace(x, y);
        // Place the tile at it's new location
        place(newX, newY, moveTile);

        // Return the coordinates as a 2-element array
        return new int[] {newX, newY};
    }

    // Move the player specifically
    public void movePlayer(char direction) {
        // Move the player
        int[] newPos = move(player.getXPos(), player.getYPos(), direction);
        // Set the player's new location
        player.setXPos(newPos[0]);
        player.setYPos(newPos[1]);
    }

    // Increment the world age by 1
    public void nextTurn() {
        turn++;
    }

    // Public method to print the world in the terminal
    public void show() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.printf("%c ", charTable[grid[i][j]]);
            }
            System.out.println();  // Go to the next line after each row
        }
    }

    // Public method to print the player's inventory
    public void showInventory() {
        for (int i = 0; i < player.getInventoryMaxSize(); i++) {
            // Skip empty item slots
            if (player.getItem(i) == null) {
                System.out.println("~ empty slot ~");
                continue;
            }

            System.out.printf("%d) %s%n", i, player.getItem(i).getName());
            System.out.printf("Durability: %d%n", player.getItem(i).getDurability());
            System.out.println("-------------------------");
        }
    }

}
import java.util.Scanner;


public class Controller {
    public static Scanner keyboard = new Scanner(System.in);

    // Clear the terminal screen
    public static void clear() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    // Make the player press enter to continue
    public static void pauseScreen() {
        System.out.print("Press ENTER to continue");
        keyboard.nextLine();
    }

    // Print the help screen
    public static void help() {
        System.out.println("Controls:");
        System.out.println("\tn to move north.");
        System.out.println("\ts to move south.");
        System.out.println("\te to move east.");
        System.out.println("\tw to move west.");
        System.out.println("\ti to open inventory.");
        System.out.println("\t? to open help.");
        System.out.println("\tq to quit.");
        System.out.println("-------------------------");
    }

    public static void main(String[] args) {
        World world = new World(20, 10, new int[] {1, 1});

        // Main game loop!
        while (true) {
            // Clear the screen
            clear();
            // Print the world
            world.show();
            System.out.println();

            System.out.print("'?' for help:  ");

            // Allow the player to move
            char direction = keyboard.nextLine().toLowerCase().charAt(0);
            try {
                world.movePlayer(direction);
                world.nextTurn();
                continue;  // Don't check next options
            } catch (IllegalArgumentException e) {}  // Invalid movement

            // Print the help screen
            if (direction == '?') {
                help();
                pauseScreen();
            } else if (direction == 'i') {
                world.showInventory();
                pauseScreen();
            } else if (direction == 'q') {
                System.exit(0);
            }
        }

    }
}
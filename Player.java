public class Player extends Entity {
    // Store character's name
    private String name;
    // Store character's x and y position in the grid
    private int xPos;
    private int yPos;
    // Store character's health
    private int health;
    // Store Player's inventory with a max of 4 items
    private Item[] inventory = new Item[4];

    // Constructor
    public Player(String name, int xPos, int yPos, int health) {
        this.name = name;
        this.xPos = xPos;
        this.yPos = yPos;
        this.health = health;
    }

    // Getters for attributes
    public String getName() { return name; }
    public int getXPos() { return xPos; }
    public int getYPos() { return yPos; }
    public int getHealth() { return health; }

    // Setters for new coordinates
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    // Health related methods
    public void takeDamage(int damage) {

    }
    public boolean isDead() {
        return health <= 0;
    }
}
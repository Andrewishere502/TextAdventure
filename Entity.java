abstract public class Entity {
    // Store character's name
    private String name;

    // Store character's x and y position in the grid
    private int xPos;
    private int yPos;
    
    // Store character's health and other stats
    private int health;
    private int strength;
    
    // Store Player's inventory with some max capacity
    private final int inventoryMaxSize;
    private Item[] inventory;

    // Basic constructor
    public Entity(String name, int xPos, int yPos, int health, int strength, int inventoryMaxSize) {
        this.name = name;

        setXPos(xPos);
        setYPos(yPos);

        this.health = health;
        this.strength = strength;

        this.inventoryMaxSize = inventoryMaxSize;
        this.inventory = new Item[inventoryMaxSize];
    }

    // Getter for name
    public String getName() { return name; }
    // Getter for X and Y position
    public int getXPos() { return xPos; }
    public int getYPos() { return yPos; }
    // Getter for health
    public int getHealth() { return health; }
    // Getter for strength
    public int getStrength() { return strength; }
    // Getter for inventory's maximum size
    public int getInventoryMaxSize() { return inventoryMaxSize; }
    
    // Return an item from the inventory
    public Item getItem(int i) {
        return inventory[i];
    }

    // Setters for new coordinates
    public void setXPos(int xPos) {
        this.xPos = xPos;
    }
    public void setYPos(int yPos) {
        this.yPos = yPos;
    }

    // Health related methods
    public void takeDamage(int damage) {
        health -= damage;
    }
    public boolean isDead() {
        return health <= 0;
    }

}
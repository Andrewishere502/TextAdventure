abstract public class Entity {
    // Store character's name
    private String name;
    // Store character's x and y position in the grid
    private int xPos;
    private int yPos;
    // Store character's health
    private int health;

    // Getters for attributes
    abstract public String getName();
    abstract public int getXPos();
    abstract public int getYPos();
    abstract public int getHealth();

    // Setters for new coordinates
    abstract public void setXPos(int xPos);
    abstract public void setYPos(int yPos);

    // Health related methods
    abstract public void takeDamage(int damage);
    abstract public boolean isDead();

}
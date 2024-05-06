abstract public class Item {
    // Store the item's name
    private String name;
    // How many times this item can be used
    private int durability;

    // Getter method
    abstract public String getName();
    abstract public int getDurability();

}
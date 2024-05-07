public class Player extends Entity {
    public Player(int xPos, int yPos) {
        // Using Entity's constructor
        super(
            "Adventurer",
            xPos, yPos,
            10,     // Health
            1,      // Strength
            4       // Max inventory size
        );
    }
}
// Item.java
// Represents an item that the player can pick up and use
// Name: John Catalana
// Date: 7/18/2025


public class Item{
    private String name;
    private String description;
    private String type; // e.g., "heal", "attack", "key"
    private int value; // amount healed or other effect value

    // Constructor
    public Item(String name, String description, String type, int value) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.value = value;
    }

    // Getter Methods
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
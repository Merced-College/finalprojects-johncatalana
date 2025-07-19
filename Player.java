// Player.java
// Represents the player character in the game
// Name: John Catalana
// Date: 7/18/2025


public class Player {
    private String name;
    private Item[] inventory; // Array to store player's items
    private int inventorySize;
    private int health;
    private int maxHealth;

    public Player(String name) {
        this.name = name;
        this.inventory = new Item[10]; // fixed-size inventory (array) Max 10 items
        this.inventorySize = 0;
        this.maxHealth = 100;
        this.health = maxHealth;
    }

    // Add an item to the player's inventory
    public boolean addItem(Item item) {
        if (inventorySize < inventory.length) {
            inventory[inventorySize++] = item;
            return true;
        }
        return false;
    }
    // Resursive method to print each item in the inventory

    public void printInventoryRecursive() {
        System.out.println("Inventory:");
        printInventoryHelper(0); // Start recursion at index 0
    }
    // Helper method for resursive inventory printing
    private void printInventoryHelper(int index) {
        if (index >= inventorySize) {
            return; // Base case
        }
        System.out.println("- " + inventory[index].getName());
        printInventoryHelper(index + 1); // Resursive step
    }

    public int getHealth() {
        return health;
    }
    
        public String getName() {
        return name;
    }


    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public boolean isAlive() {
        return health > 0;
    }

    // Print player health/status
    public void printStatus() {
        System.out.println(name + " | HP: " + health + "/" + maxHealth);
    }

    // Use an item by name
    public void useItem(String itemName) {
        for (int i = 0; i < inventorySize; i++) {
            if (inventory[i].getName().equalsIgnoreCase(itemName)) {
                Item item = inventory[i];

            switch (item.getType()) {
                case "heal":
                    System.out.println("You use " + item.getName() + " and heal" + item.getValue() + " HP.");
                    health += item.getValue(); // Heal the player
                    if (health > maxHealth) health = maxHealth;
                    removeItem(i); // remove used item
                    return;

                default:
                    System.out.println("Nothing happens... yet.");
                    return;
            }
        }
    }
        System.out.println("You don't hae that item.");
    }

    // Remove item from inventory at given index
    private void removeItem(int index) {
        for (int i = index; i < inventorySize - 1; i++) {
            inventory[i] = inventory[i + 1]; // Shift left
        }
        inventory[inventorySize - 1] = null;
        inventorySize--;
    }
}    
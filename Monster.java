// Monster.java
// Represents an enemy monster in the dungeon
// Name: John Catalana
// Date: 7/18/2025

public class Monster {
    private String name;
    private int health;
    private int attackPower;
    private Item dropItem; // Item dropped when defeated

    // Constructor
    public Monster(String name, int health, int attackPower) {
        this(name, health, attackPower, null); // default: no drop
    }

    // Constructor with item drop
    public Monster(String name, int health, int attackPower, Item dropItem) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.dropItem = dropItem;
    }

    // Check if monster is alive
    public boolean isAlive() {
        return health > 0;
    }

    //Reduce health by amount taken
    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }
    // Monster attacks and returns damage dealt
    public int attack() {
        return attackPower;
    }

    public int getHealth() {
        return health;
    }
    
    public Item getDropItem() {
        return dropItem;
    }

    public String getName() {
        return name;
    }
}
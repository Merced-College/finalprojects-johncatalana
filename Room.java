// Room. java
// Represents a room in the dungeon, including exits, monsters, and items found
// Name: John Catalana
// Date: 7/18/2025

import java.util.LinkedList;
import java.util.Queue;
import java.util.Map;
import java.util.HashMap;

public class Room {
    private String id;
    private String description;
    private Queue<Monster> monsters; // example of linked list (queue of monsters in the room)
    private Item item; // Item in the room (if any at all)
    private Map<String, String> exits; // direction --> roomId

    // Constructor
    public Room(String id, String description, Item item) {
        this.id = id;
        this.description = description;
        this.monsters = new LinkedList<>();
        this.item = item;
        this.exits = new HashMap<>();
    }

    public String getId() {
        return id;
    }
    
    // Get description of the room
    public String getDescription() {
        return description;
    }

    // Add a monster to the room
    public void addMonster(Monster monster) {
        monsters.add(monster);
    }

    // Return the queue of monsters
    public Queue<Monster> getMonsters() {
        return monsters;
    }

    // Check if room contains any living monsters
    public boolean hasMonsters() {
        return !monsters.isEmpty();
    }

    // Get the item in the room
    public Item getItem() {
        return item;
    }

        //Set an item in the room
    public void setItem(Item item) {
        this.item = item;
    }

    // Add an exit to the room (with direction so "north" --> "hall" depending on what room you're in)
    public void setExit(String direction, String roomId) {
        exits.put(direction.toLowerCase(), roomId);
    }

    // Get the room ID that the given direction leads to
    public String getExit(String direction) {
        return exits.get(direction.toLowerCase());
    }

    // Print all available exits
    public void printExits() {
        System.out.println("Exits: " + String.join(" , ", exits.keySet()));
    }

}
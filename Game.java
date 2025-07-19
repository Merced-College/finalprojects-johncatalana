// Game.java
// Handles room setup, game loop, and command handling
// Name: John Catalana
// Date: July 18, 2025

import java.util.*;
import java.util.Stack;

public class Game {
    private Player player = new Player("Hero"); //creates the player
    private HashMap<String, Room> dungeonMap; // hash table and stores room layout
    private Stack<String> roomHistory = new Stack<>(); // undo stack used for backtracking
    private String currentRoomId; // tracks the current room

    public Game() {
        player = new Player("Hero");
        dungeonMap = new HashMap<>();
        roomHistory = new Stack<>();
        setupDungeon();
    }
    // Initializes dungeon layout, connections, monsters, and items
    private void setupDungeon() {
        //Setup rooms with IDs and connect them via logic and you can add items into the rooms
        Room Entrance = new Room("Entrance", "You are at the dark entrance of the dungeon.", new Item("Health Potion", "Restores 25 HP", "heal", 25));
        Room Hall = new Room("Hall", "You are in a long hallway with flickering lights.", null);
        Room Chamber = new Room("Chamber" , "You find yourself in a small chamber. Something is watching you...", null);
        Room Armory = new Room("Armory", "Old rusty weapons are scattered around.", null);
        Room Library = new Room("Library", "Books about forgotten magic fill the room.", null);

        // Add monsters
        Hall.addMonster(new Monster("Goblin", 20, 5, new Item("Health Potion", "Restores 25 HP", "heal", 25)));
        Chamber.addMonster(new Monster("Bat", 10, 3));
        Library.addMonster(new Monster("Big Rat", 15, 5));

        // Connect rooms using directions
        Entrance.setExit("north", "Hall");
        Hall.setExit("south", "Entrance");
        Hall.setExit("east", "Chamber");
        Hall.setExit("north", "Library");
        Hall.setExit("west", "Armory");
        Armory.setExit("east", "Hall");
        Library.setExit("south", "Hall");
        Chamber.setExit("west", "Hall");

        // Add rooms to map
        dungeonMap.put("Entrance" , Entrance);
        dungeonMap.put("Hall", Hall);
        dungeonMap.put("Chamber", Chamber);
        dungeonMap.put("Library", Library);
        dungeonMap.put("Armory", Armory);

        currentRoomId = "Entrance";
    }

    
    public static void main(String[] args) {
        Game game = new Game(); // Initalize rooms, monsters, and items
        game.start(); // Begin game loop
    }

    // Main game loop
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean playing = true;

        while (playing) {
            Room currentRoom = dungeonMap.get(currentRoomId);
            System.out.println(currentRoom.getDescription());
            currentRoom.printExits();

            System.out.println("Commands: move <direction>, take, use <item> undo, inventory, status, quit");
            String input = scanner.nextLine().trim().toLowerCase();
            String[] parts = input.split(" " );
            String command = parts[0];

            switch (command) {
                case "move":
                    if (parts.length < 2) {
                        System.out.println("Please specifiy a direction (north, south, east, west).");
                        break;
                    }

                    String direction = parts[1];
                    String nextRoomId = currentRoom.getExit(direction);

                    if (nextRoomId == null) {
                        System.out.println("You can't go that way.");
                        break;
                    }

                    roomHistory.push(currentRoomId); // Save where we were
                    currentRoomId = nextRoomId; // Then update where we're going

                    Room nextRoom = dungeonMap.get(currentRoomId);
                    System.out.println("\n" + nextRoom.getDescription());
                    nextRoom.printExits();

                    Item roomItem = nextRoom.getItem();
                    if (roomItem != null) {
                        System.out.println("You see a " + roomItem.getName() + " here. " + roomItem.getDescription());
                    }

                    if (nextRoom.hasMonsters()) {
                        fightMonsters(nextRoom);
                    }
                    break;
                case "inventory":
                    player.printInventoryRecursive();
                    break;
                case "status":
                    player.printStatus();
                    break;
                case "undo":
                    if (roomHistory.isEmpty()) {
                        System.out.println("No previous room to return to.");
                    } else {
                        currentRoomId = roomHistory.pop(); // Go back one room
                        Room backRoom = dungeonMap.get(currentRoomId);
                        System.out.println("You backtracked to the previous room");
                        System.out.println(backRoom.getDescription());
                        backRoom.printExits(); // shows the avaiable exits in that room

                        roomItem = backRoom.getItem();
                        if (roomItem != null) {
                            System.out.println("You see a " + roomItem.getName() + " here. " + roomItem.getDescription());
                        }

                        if (backRoom.hasMonsters()) {
                        fightMonsters(backRoom);
                        }
                    }
                    break;
                case "take":
                    Room room = dungeonMap.get(currentRoomId);
                    Item item = room.getItem();
                    if (item != null) {
                        boolean added = player.addItem(item);
                        if (added) {
                            System.out.println("You picked up: " + item.getName());
                            room.setItem(null); // Remove it from the room
                        } else {
                            System.out.println("Your inventory is full.");
                        }
                    } else {
                        System.out.println("There's nothing to take.");
                    }
                    break;
                case "use":
                    if (parts.length < 2) {
                        System.out.println("Use what?");
                        break;
                    }
                    String itemName = input.substring(input.indexOf(" ") + 1); // full item name
                    player.useItem(itemName);
                    break;
                case "quit":
                    playing = false;
                    break;
                default:
                    System.out.println("Unknown command.");
            }
        }

            System.out.println("Thanks for playing!");

    }

    // Handles player-monster combat per room
    private void fightMonsters(Room room) {
        Queue<Monster> monsters = room.getMonsters();
        Scanner scanner = new Scanner(System.in);

        System.out.println("You've encountered enemies!");

        while(!monsters.isEmpty()) {
            Monster monster = monsters.peek();
            System.out.println("A " + monster.getName() + " appears with " + monster.getHealth() + " HP. ");

            System.out.println("Commands: attack, run");
            String input = scanner.nextLine();

            if (input.equals("attack")) {
                monster.takeDamage(10); // Player does flat 10 damage
                System.out.println("You hit the " + monster.getName() + " for 10 damage");

                if (!monster.isAlive()) {
                    System.out.println("You defeated the " + monster.getName() + "!");
                    Item drop = monster.getDropItem();
                    monsters.poll(); // remove defeated monster
                    if (drop!= null) {
                        System.out.println("It dropped a " + drop.getName() + "!");
                        Room currentRoom = dungeonMap.get(currentRoomId);
                        if (currentRoom.getItem() == null) {
                            currentRoom.setItem(drop);
                        } else {
                            System.out.println("But there's already an item here, so the drop disappears");
                        }
                    }
                } else {
                    // Recursive monster retaliation
                    System.out.println("The monsters retaliate!");
                    Queue<Monster> copy = new LinkedList<>(monsters);
                    monsterChainAttack(copy,0);
                }
            } else if (input.equals("run")) {
                System.out.println("You fled the room!");
                return;
            } else {
                System.out.println("Unknown command.");
            }
        }

        System.out.println("You've defeated all the monsters!");
    }

    // Recursive function that lets all monsters attack one by one
    private void monsterChainAttack(Queue<Monster> monstersCopy, int monsterIndex) {
        if (monstersCopy.isEmpty()) {
            return; // base case: no more monsters
        }
        Monster monster = monstersCopy.poll();
        if (monster.isAlive()) {
            int damage = monster.attack();
            player.takeDamage(damage);
            System.out.println(monster.getName() + " attacks you for " + damage + " damage!");
            System.out.println("Your HP: " + player.getHealth());
        }
        // Recurse into next monster
        monsterChainAttack(monstersCopy, monsterIndex + 1);
    }
}
import java.util.Scanner;
import java.util.ArrayList;


public class App {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        clear();
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        clear();

        System.out.println("Welcome, " + name + "!");
        System.out.println("Here you have 1000 gold to spend.");
        Player player = new Player(name, 100, 100);
        player.getInventory().addGold(1000);
        System.out.println();
        System.out.println("Press enter to continue...");
        scanner.nextLine();
        Weapon weapon = new Weapon("Sword of the gods", 800, 300, 10, 30, 0, 200, "Slash", 1);
        Armor helmet = new Armor("Golden helmet", 1000, 500, 12, 20, 100, "Helmet" );
        Armor chestplate = new Armor("Titanium chestplate", 1500, 1000, 20, 35, 100, "Chestplate" );
        Consumable healthPotion = Consumable.createRandomHealthPotion();
        player.getInventory().addItem(healthPotion);
        player.getInventory().addItem(helmet);
        player.getInventory().addItem(chestplate);
        player.getInventory().addItem(weapon);
        weapon.equipItem(player.getInventory());
        chestplate.equipItem(player.getInventory());
        helmet.equipItem(player.getInventory());

        boolean running = true;
        while (running) {
            clear();
            System.out.println("=== Main Menu ===");
            System.out.println("1. Show player info");
            System.out.println("2. Show player inventory");
            System.out.println("3. Fight enemy");
            System.out.println("4. Go to shop");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Rensa input

            switch (choice) {
                case 1: 
                    clear();
                    player.printInfo();
                    System.out.println();
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    break;

                case 2:
                    clear();

                    // Hämta alla föremål
                    ArrayList<Item> items = player.getInventory().getItems();  // Unequipped items
                    ArrayList<Item> equippedItems = player.getInventory().getEquippedItems();  // Equipped items

                    // Skriv ut alla föremål
                    System.out.println("=== Inventory Items ===");
                    for (int i = 0; i < items.size(); i++) {
                        System.out.println((i + 1) + ". " + items.get(i).getName());
                    }

                    System.out.println("\n=== Equipped Items ===");
                    for (int i = 0; i < equippedItems.size(); i++) {
                        System.out.println((items.size() + i + 1) + ". " + equippedItems.get(i).getName());
                    }

                    System.out.println();
                    System.out.println("Choose an item or press enter to go back...");
                    String input = scanner.nextLine();

                    if (input.isEmpty()) {
                        break;
                    }

                    int inventoryChoice;
                    // Försök att parsa input till ett heltal
                    try {
                        inventoryChoice = Integer.parseInt(input);
                    } catch (NumberFormatException e) {
                        System.out.println("Ogiltigt val. Försök igen.");
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    }

                    // Kontrollera att valet är inom giltigt intervall
                    if (inventoryChoice < 1 || inventoryChoice > (items.size() + equippedItems.size())) {
                        System.out.println("Ogiltigt val. Försök igen.");
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        break;
                    }

                    // Om valet är inom gränserna, välj motsvarande föremål
                    Item selectedItem;
                    if (inventoryChoice <= items.size()) {
                        // Väljer ett unequipped item
                        selectedItem = items.get(inventoryChoice - 1);
                    } else {
                        // Väljer ett equipped item
                        selectedItem = equippedItems.get(inventoryChoice - items.size() - 1); 
                    }

                    // Visa information om det valda föremålet
                    clear();
                    System.out.println("You selected: " + selectedItem.getName());
                    System.out.println();


                    // Ny meny efter att ett föremål valts
                    if (selectedItem instanceof Consumable) {
                        ((Consumable) selectedItem).printInfo();

                        System.out.println("\n=== Item Menu ===");
                        System.out.println("1. Consume");
                        System.out.println("2. Throw away");
                        System.out.println("3. Go back");
                        System.out.println("Choose an option:");

                        String actionInput = scanner.nextLine();
                        switch (actionInput) {
                            case "1":
                                ((Consumable) selectedItem).useItem(player.getInventory(), player);
                                break;
                            case "2":
                                player.getInventory().removeItem(selectedItem);
                                break;
                            case "3":
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }

                    }
                    if (selectedItem instanceof Weapon || selectedItem instanceof Armor) {
                        boolean isEquipped = player.getInventory().getEquippedItems().contains(selectedItem);
                        if (selectedItem instanceof Weapon) {
                            ((Weapon) selectedItem).printInfo();
                        } else {
                            ((Armor) selectedItem).printInfo();
                        }

                        System.out.println("\n=== Item Menu ===");
                        if (isEquipped) {
                            System.out.println("1. Unequip");
                        } else {
                            System.out.println("1. Equip");
                        }

                        System.out.println("2. Repair");
                        System.out.println("3. Throw away");
                        System.out.println("4. Go back");
                        System.out.println("Choose an option:");

                        String actionInput = scanner.nextLine();
                        switch (actionInput) {
                            case "1":
                                clear();
                                if (isEquipped) {
                                    ((Equippable) selectedItem).unEquipItem(player.getInventory());

                                    System.out.println();
                                    System.out.println("Press enter to continue...");
                                    scanner.nextLine();
                                } else {
                                    ((Equippable) selectedItem).equipItem(player.getInventory());

                                    System.out.println();
                                    System.out.println("Press enter to continue...");
                                    scanner.nextLine();
                                }


                            case "2":
                                ((Equippable) selectedItem).repairDurability(player.getInventory());
                                break;
                            case "3":
                                player.getInventory().removeItem(selectedItem);
                                break;
                            case "4":
                                break;
                            default:
                                System.out.println("Invalid choice. Please try again.");
                                break;
                        }


                    }
                    break;
                    
                case 3:
                    clear();
                    fightEnemy(player);
                    if (player.getCurrentHealth() <= 0) {
                        System.out.println("You have been defeated!");

                        System.out.println();
                        System.out.println("Press enter to continue...");
                        scanner.nextLine();
                        running = false;
                    }
                    break;

                case 4:
                    clear();
                    Shop shop = new Shop("Adventurer's Emporium");
                    shop.shopMenu(player);
                    break;

                case 5:
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                
            }
        }
        scanner.close();
    }

    // Metod för att slåss mot fiende
    public static void fightEnemy(Player player) {
        Enemy enemy = new Enemy();
        Scanner scanner = new Scanner(System.in);

        clear();
        System.out.println("A wild " + enemy.getName() + " has appeared!");
        enemy.printInfo();

        System.out.println();
        System.out.println("Press enter to start the fight...");
        scanner.nextLine();

        // Loop
        while (player.getCurrentHealth() > 0 && enemy.isAlive()) {
            clear();
            System.out.println("Your turn! press Enter to attack!");
            scanner.nextLine();
            clear();

            // Potion rekomendationer
            if (player.getCurrentHealth() < player.getMaxHealth() * 0.3) {
                    System.out.println("Warning, You are low on health! Consider using a health potion.");
                    if (player.getInventory().hasConsumable("Health")) {
                        System.out.println("You have a health potion in your inventory. Would you like to use it? (yes/no)");
                        
                        String response = scanner.nextLine().toLowerCase();
                        if (response.equals("yes")) {
                            clear();
                            for (Item item : player.getInventory().getItems()) {
                                if (item instanceof Consumable && ((Consumable)item).getConsumableType().equals("Health")) {
                                    Consumable consumable = (Consumable) item;
                                    consumable.useItem(player.getInventory(), player);
                                    break;
                                }
                            }
                        }
                    } else {
                        System.out.println("You don't have a health potion in your inventory.");
                    }
                    System.out.println();
                    System.out.println("Press enter to continue...");
                    scanner.nextLine();
                    clear();
                }
            

            // Om användaren har ett vapen equippat så attackerar användaren med vapnet
            if (player.getInventory().GetEquippedWeapon() != null) {
                System.out.println("You attacked the " + enemy.getName() + " with your " + player.getInventory().GetEquippedWeapon().getName() + "!");
                enemy.takeDamage(player.getInventory().GetEquippedWeapon().useItem(player.getInventory(), player), player);

                System.out.println();
                System.out.println("Press enter to continue...");
                scanner.nextLine();
            } else {
                System.out.println("You don't have a weapon equipped!");
                System.out.println("You had to run away!");

                System.out.println("Press enter to continue...");
                scanner.nextLine();
                return;
            }

            // Kollar om fienden är död
            if (!enemy.isAlive()) {
                clear();
                return;
            }

            
            // Om fienden har ett vapen equippat så attackerar den
            if (enemy.getInventory().GetEquippedWeapon() != null) {
                clear();
                System.out.println("The " + enemy.getName() + " attacks you!");
                player.takeDamage(enemy.getInventory().GetEquippedWeapon().useItem(enemy.getInventory(), player));
                System.out.println();
                System.out.println("Press enter to continue...");
                scanner.nextLine();
            } else {
                System.out.println("The " + enemy.getName() + " had no  weapon equipped!");
            }

            if (player.getCurrentHealth() <= 0) {
                System.out.println("Press enter to continue...");
                scanner.nextLine();
                return;
            }
        }
    }

    // Metod för att tömma konsollen
    public static void clear() { 
        for (int i = 0; i < 50; ++i) { 
            System.out.println(); 
        } 
    } 
}

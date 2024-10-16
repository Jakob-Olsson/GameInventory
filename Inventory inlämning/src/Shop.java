import java.util.Scanner;

public class Shop {
    private String name;
    private Inventory inventory;

    public Shop(String name) {
        this.name = name;
        this.inventory = new Inventory();
        initializeShop();
    }

    private void initializeShop() {
        inventory.addItem(new Weapon("Blade of Eternal Flames", 1500, 700, 8, 50, 0, 100, "Fire Slash", 1));
        inventory.addItem(new Weapon("Thunderfury, Blessed Blade of the Windseeker", 2000, 850, 6, 60, 0, 120, "Thunder Strike", 1));
        inventory.addItem(new Weapon("Doomhammer", 1800, 900, 10, 55, 0, 90, "Earthquake Slam", 1));
        inventory.addItem(new Weapon("Frostmourne", 2200, 1000, 7, 65, 0, 110, "Ice Cleave", 1));
        inventory.addItem(new Weapon("Shadowfang", 1700, 750, 5, 45, 0, 95, "Dark Slice", 1));
        inventory.addItem(new Armor("Dragon Scale Helm", 1000, 600, 3, 40, 80, "Helmet"));
        inventory.addItem(new Armor("Guardian's Aegis", 1200, 700, 5, 50, 100, "Shield"));
        inventory.addItem(new Armor("Titanium Legplates", 1100, 650, 6, 45, 90, "Pants"));
        inventory.addItem(new Armor("Celestial Robes of the Archmage", 1300, 800, 2, 35, 75, "Chest"));
        inventory.addItem(new Armor("Boots of the Abyss", 900, 500, 2, 30, 70, "Boots"));
    }

    public void showShopItems() {
        System.out.println("=== " + name + " ===");
        System.out.println("Here are the items available for purchase.");
        System.out.println();
        int index = 1;
        for (Item item : inventory.getItems()) {
            System.out.println(index + ". " + item.getName() + " - Cost: " + item.getCost() + " gold");
            index++;
        }
    }

    public void buyItem(Player player, int itemIndex) {
        Item itemToBuy = inventory.getItem(itemIndex - 1);

        if (itemToBuy == null) {
            clear();
            System.out.println("Invalid item choise.");
            waitForEnter();

            return;
        }

        if (player.getInventory().getGold() >= itemToBuy.getCost()) {
            player.getInventory().addItem(itemToBuy);
            player.getInventory().removeGold(itemToBuy.getCost());
            inventory.removeItem(itemToBuy);
            clear();
            System.out.println("You bought " + itemToBuy.getName() + " for " + itemToBuy.getCost() + " gold.");
        } else {
            clear();
            System.out.println("You don't have enough gold to buy this item.");
        }
        waitForEnter();

    }

    public void sellItem(Player player, int itemIndex) {
        Item itemToSell = player.getInventory().getItem(itemIndex - 1);

        if (itemToSell == null) {
            clear();
            System.out.println("Invalid item choise.");
            waitForEnter();

            return;
        }

        int sellPrice = itemToSell.getCost();
        player.getInventory().addGold(sellPrice);
        player.getInventory().removeItem(itemToSell);
        inventory.addItem(itemToSell);
        clear();
        System.out.println("You sold " + itemToSell.getName() + " for " + sellPrice + " gold.");
        waitForEnter();
    }

    public void shopMenu(Player player) {
        Scanner scanner = new Scanner(System.in);
        boolean shopping = true;

        while (shopping) {
            clear();
            System.out.println("=== Welcome to " + name + " ===");
            System.out.println("1. Buy items");
            System.out.println("2. Sell items");
            System.out.println("3. Exit shop");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    clear();
                    showShopItems();
                    System.out.println();
                    System.out.println("Enter the number of the item you want to buy:");
                    int itemToBuy = scanner.nextInt();
                    buyItem(player, itemToBuy);
                    break;

                case 2:
                    clear();
                    player.getInventory().showItems();
                    System.out.println();
                    System.out.println("Enter the number of the item you want to sell:");
                    int itemToSell = scanner.nextInt();
                    sellItem(player, itemToSell);
                    break;

                case 3:
                    shopping = false;
                    break;

                default:
                    System.out.println("Invalid choice.");
                    waitForEnter();

            }

        }

    }

    // Metod för att vänta på att användaren trycker enter
    public static void waitForEnter() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Press enter to continue...");
        scanner.nextLine();
    }

    // Metod för att skriva ut en tom rad
    public static void clear() { 
        for (int i = 0; i < 50; ++i) { 
            System.out.println(); 
        } 
    } 

    public String getName() {
        return name;
    }

    public Inventory getInventory() {
        return inventory;
    }
    
}

import java.util.Random;

public class Enemy {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private Inventory inventory;


    // Konstruktor
    public Enemy() {
        Random random = new Random();
        String[] names = {"Goblin", "Orc", "Troll", "Dragon", "Giant"};
        this.name = names[random.nextInt(names.length)];
        this.maxHealth = 50 + random.nextInt(151);
        this.currentHealth = maxHealth;
        this.inventory = new Inventory();
        this.inventory.addGold(100 + random.nextInt(901));

        createRandomArmor();
        createRandomWeapon();

    }

    // Skapa random vapen
    private void createRandomWeapon() {
        Random random = new Random();
        String[] weaponNames = {"Sword of Destiny", "Axe of Fury", "Dagger of Shadows", "Hammer of the Hunt", "Sword of evil"};
        String name = weaponNames[random.nextInt(weaponNames.length)];
        int cost = 100 + random.nextInt(200); // Mellan 100 och 300
        int repairCost = 50 + random.nextInt(100); // Mellan 50 och 150
        int weight = 5;
        int durability = 50 + random.nextInt(51);
        int meleeDamage = 10 + random.nextInt(21);
        int rangedDamage = 0;
        String effect = "Damage";
        int range = 1;
        Weapon weapon = new Weapon(name, cost, repairCost, weight, meleeDamage, rangedDamage, durability, effect, range);
        this.inventory.addItem(weapon);
        weapon.equipItem(this.inventory);
    }

    // Skapa random armor
    private void createRandomArmor() {
        Random random = new Random();
        String[] armorNames = {"Iron Armor", "Leather Armor", "Mystic Robes", "Dragon Scale Armor", "Steel Plate Armor"};
        String name = armorNames[random.nextInt(armorNames.length)];

        int cost = 100 + random.nextInt(200); // Mellan 100 och 300
        int repairCost = 50 + random.nextInt(100); // Mellan 50 och 150
        int weight = 5;
        int defense = 20 + random.nextInt(21); // Mellan 20 och 40
        int durability = 50 + random.nextInt(51); // Mellan 50 och 100
        String[] armorTypeList = {"Helmet", "Chestplate", "Pants", "Boots"};
        String typeOfArmor = armorTypeList[random.nextInt(armorTypeList.length)];
        Armor armor = new Armor(name, cost, repairCost, weight, defense, durability, typeOfArmor);
        this.inventory.addItem(armor);
        armor.equipItem(this.inventory);
    }
    

    // Skriv ut information
    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.println("Health: " + currentHealth + "/" + maxHealth);
        System.out.println("Gold: " + inventory.getGold());
        this.inventory.showEquippedItems();
    }


    // Ta skada och kalla på drop loot
    public void takeDamage(int incomingDamage, Player player) {
        int totalDefense = 0;

        for (Item item : inventory.getEquippedItems()) {
            if (item instanceof Armor) {
                Armor armor = (Armor) item;
                totalDefense += armor.getDefense();
            }
        }

        int reducedDamage = Math.max(1, incomingDamage - (incomingDamage * totalDefense / 100));
        currentHealth -= reducedDamage;
        System.out.println(name + " took " + reducedDamage + " damage! Health is now " + currentHealth + "/" + maxHealth + ".");
        System.out.println();

        if (currentHealth <= 0) {
            System.out.println(name + " has been defeated!");
            System.out.println();
            dropLoot(player);
        }

    }


    // Droppa loot
    public void dropLoot(Player player) {
        System.out.println("Enemy dropped " + inventory.getGold() + " gold and items!");
        player.getInventory().addGold(inventory.getGold());

        // Hämta alla items från inventory
        for (Item item : inventory.getItems()) {
            player.getInventory().addItem(item);
        }
        // Hämta alla equipped items
        for (Item item : inventory.getEquippedItems()) {
            player.getInventory().addItem(item);
        }
    }


    public Inventory getInventory() {
        return inventory;
    }

    public String getName() {
        return name;
    }

    public boolean isAlive() {
        return currentHealth > 0;
    }


}

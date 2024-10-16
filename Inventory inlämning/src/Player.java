public class Player {
    private String name;
    private int currentHealth;
    private int maxHealth;
    private int maxWeight;
    private int currentWeight;
    private Inventory inventory;

    public Player(String name, int health, int maxWeight) {
        this.name = name;
        this.currentHealth = health;
        this.maxHealth = health;
        this.maxWeight = maxWeight;
        this.currentWeight = 0;
        this.inventory = new Inventory();
    }

    public void printInfo() {
        System.out.println("Name: " + name);
        System.out.println("Health: " + currentHealth + "/" + maxHealth);
        System.out.println("weight: " + currentWeight + "/" + maxWeight);
        System.out.println("Gold: " + inventory.getGold());
    }

    public Inventory getInventory() {
        return inventory;
    }

    // Ta skada
    public void takeDamage(int incomingDamage) {
        int totalDefense = 0;

        for (Item item : inventory.getEquippedItems()) {
            if (item instanceof Armor) {
                Armor armor = (Armor) item;
                totalDefense += armor.getDefense();
            }
        }

        int reducedDamage = Math.max(1, incomingDamage - (incomingDamage * totalDefense / 100));
        currentHealth -= reducedDamage;
        System.out.println("You took " + reducedDamage + " damage! Health is now " + currentHealth + "/" + maxHealth + ".");

        if (currentHealth <= 0) {
            System.out.println("You have been defeated!");
        }

    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public void heal(int amount) {
        // Ej över maxHealth
        currentHealth = Math.min(currentHealth + amount, maxHealth);
    }

    public int getMaxHealth() {
        return maxHealth;
    }

}

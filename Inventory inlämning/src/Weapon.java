public class Weapon extends Item implements Equippable, Usable {
    private int repairCost;
    private int meleeDamage;
    private int rangedDamage;
    private int durability;
    private int currentDurability;
    private String effect;
    private int range;

    // Konstruktor
    public Weapon(String name, int cost, int repairCost, double weight, int meleeDamage, int rangedDamage, int durability, String effect, int range) {
        super(name, cost, "Weapon", weight);
        this.repairCost = repairCost;
        this.meleeDamage = meleeDamage;
        this.rangedDamage = rangedDamage;
        this.durability = durability;
        this.currentDurability = durability;
        this.effect = effect;
        this.range = range;
    }

    // Skriv ut information
    public void printInfo() {
        System.out.println("Name: " + getName());
        System.out.println("Cost: " + getCost());
        System.out.println("Repair Cost: " + repairCost);
        System.out.println("Type: " + getType());
        System.out.println("Weight: " + getWeight());
        System.out.println("Melee Damage: " + meleeDamage);
        System.out.println("Ranged Damage: " + rangedDamage);
        System.out.println("Durability: " + durability);
        System.out.println("Effect: " + effect);
        System.out.println("Range: " + range);
    }

    public void equipItem(Inventory inventory) {
        if (currentDurability > 0) {
            // Kollar så där inte redan finns ett annat vapen.
            for (Item item : inventory.getEquippedItems()) {
                if (item instanceof Weapon) {
                        System.out.println("You already have a weapon equipped.");
                        return;
                }
            }
            if (!inventory.getEquippedItems().contains(this)) {
                inventory.addEquippedItem(this);
                inventory.removeItem(this);
                System.out.println(getName() + " has been equipped.");
            } else {
                System.out.println("Item is already equipped.");
            }
        } else {
            System.out.println("Item cannot be equipped due to being broken.");
        }
    }

    public void unEquipItem(Inventory inventory) {
        if (inventory.getEquippedItems().contains(this)) {
            inventory.addItem(this);
            inventory.removeEquippedItem(this);
            System.out.println(getName() + " has been unequipped.");
        } else {
            System.out.println("Item is not currently equipped.");
        }
    }

    public void repairDurability(Inventory inventory) {
        if (repairCost <= inventory.getGold()) {
            inventory.setGold(inventory.getGold() - repairCost);
            currentDurability = durability;
            System.out.println("Durability restored to " + currentDurability + ".");
        } else {
            System.out.println("Not enough gold to repair durability.");
        }
    }

    // Damage to items durability
    public void durabilityDamage(Inventory inventory) {
        currentDurability --;
        System.out.println("Durability decreased to " + currentDurability + ".");
        if (currentDurability <= 0) {
            System.out.println("Item has been broken.");
            this.unEquipItem(inventory);
        }
    }

    public int useItem(Inventory inventory, Player player) {
        if (currentDurability > 0) {
            // System.out.println("You used " + getName() + ".");
            // System.out.println("Effect: " + effect);
            durabilityDamage(inventory);
            if (meleeDamage > rangedDamage) {
                return meleeDamage;
            } else {
                return rangedDamage;
            }
        } else {
            System.out.println("Item cannot be used due to being broken.");
        }
        return 0;
    }
}

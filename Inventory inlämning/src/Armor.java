public class Armor extends Item implements Equippable {
    private int repairCost;
    private int defense;
    private int durability;
    private int currentDurability;
    private String typeOfArmor;

    // Konstruktor
    public Armor(String name, int cost, int repairCost, double weight, int defense, int durability, String typeOfArmor) {
        super(name, cost, "Armor", weight);
        this.repairCost = repairCost;
        this.defense = defense;
        this.durability = durability;
        this.currentDurability = durability;
        this.typeOfArmor = typeOfArmor;
    }

    // Skriv ut information
    public void printInfo() {
        System.out.println("Name: " + getName());
        System.out.println("Cost: " + getCost());
        System.out.println("Repair Cost: " + repairCost);
        System.out.println("Type: " + getType());
        System.out.println("Weight: " + getWeight());
        System.out.println("Defense: " + defense);
        System.out.println("Durability: " + durability);
        System.out.println("Type of Armor: " + typeOfArmor);
    }

    public void equipItem(Inventory inventory) {
        if (currentDurability > 0) {
            // Kollar så där inte redan finns en annan armor av samma typ.
            for (Item item : inventory.getEquippedItems()) {
                if (item instanceof Armor) {
                    Armor equippedArmor = (Armor) item;
                    if (equippedArmor.typeOfArmor.equals(this.typeOfArmor)) {
                        System.out.println("You already have an armor of type: " + typeOfArmor + " equipped.");
                        return;
                    }
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

    public int getDefense() {
        return defense;
    }

}

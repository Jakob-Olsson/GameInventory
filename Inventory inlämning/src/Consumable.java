import java.util.Random;

public class Consumable extends Item implements Usable {
    private int amount;
    private String consumableType;

    public Consumable(String name, int cost, double weight, int amount, String consumableType) {
        super(name, cost, "Consumable", weight);
        this.amount = amount;
        this.consumableType = consumableType;
    }

    public void printInfo() {
        System.out.println("Name: " + getName());
        System.out.println("Cost: " + getCost());
        System.out.println("Type: " + getType());
        System.out.println("Weight: " + getWeight());
        System.out.println("Amount: " + amount);
        System.out.println("Consumable type: " + consumableType);
    }

    public static Consumable createRandomHealthPotion() {
        Random random = new Random();
        int randomAmount = 50 + random.nextInt(91);
        return new Consumable("Health Potion", 100, 0.5, randomAmount, "Health");
    }

    public int useItem(Inventory inventory, Player player) {
        if (consumableType.equals("Health")) {
            System.out.println("You used " + getName() + " and gained " + amount + " health.");
            player.heal(amount);
            inventory.removeItem(this);
            return amount;
        } else {
            return 0;
        }
    }

    public String  getConsumableType() {
        return consumableType;
    }

}

import java.util.ArrayList;

public class Inventory {
    private ArrayList<Item> items;
    private ArrayList<Item> equippedItems;
    private int gold;


    public Inventory() {
        items = new ArrayList<>();
        equippedItems = new ArrayList<>();
        gold = 0;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void addGold(int amount) {
        gold += amount;
        System.out.println("You now have " + gold + " gold in your inventory.");
    }

    public void removeGold(int amount) {
        gold -= amount;
        System.out.println("You now have " + gold + " gold in your inventory.");
    }

    public void sellItem(Item item) {
        items.remove(item);
        addGold(item.getCost());
        System.out.println("Item sold. You now have " + gold + " gold in your inventory.");
    }

    public void showItems() {
        System.out.println();
        System.out.println("=== Inventory Items ===");
        System.out.println();
        if(items.isEmpty()) {
            System.out.println("No items in inventory.");
            return;
        }
        ArrayList<Item> items = getItems();
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }
    }

    public void showEquippedItems() {
        System.out.println();
        System.out.println("=== Equipped Items ===");
        System.out.println();
        if(equippedItems.isEmpty()) {
            System.out.println("No items equipped.");
            return;
        }
        ArrayList<Item> items = getEquippedItems();
        for (int i = 0; i < items.size(); i++) {
            System.out.println((i + 1) + ". " + items.get(i).getName());
        }
    }

    // Lägger till ett föremål i equippedItems
    public void addEquippedItem(Item item) {
        equippedItems.add(item);
    }

    // Tar bort ett föremål från equippedItems
    public void removeEquippedItem(Item item) {
        equippedItems.remove(item);
    }

    public boolean hasConsumable(String type) {
        for (Item item : items) {
            if (item instanceof Consumable && ((Consumable) item).getConsumableType().equals(type)) {
                return true;
            }
        }
        return false;
    }



    // Getters och setters 

    public Weapon GetEquippedWeapon() {
        for (Item item : equippedItems) {
            if (item instanceof Weapon) {
                return (Weapon) item;
            }
        }
        return null;
    }

    public ArrayList<Item> getEquippedItems() {
        return equippedItems;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }   

    public Item getItem(int index) {
        return items.get(index);
    }
}
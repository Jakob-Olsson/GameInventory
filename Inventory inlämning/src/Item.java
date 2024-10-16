abstract public class Item {
    private String name;
    private int cost;
    private String type;
    private double weight;

    public Item(String name, int cost, String type, double weight) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getWeight() {
        return weight;
    }
}

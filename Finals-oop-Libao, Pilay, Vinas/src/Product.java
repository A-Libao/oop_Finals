public class Product {
    private int id;
    private String name;
    private String description;
    private double price;

    public Product(int id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    @Override
    public String toString() {
        return id + "," + name + "," + description + "," + price;
    }

    // Static method for deserialization
    public static Product fromString(String line) {
        String[] parts = line.split(",");
        return new Product(Integer.parseInt(parts[0]), parts[1], parts[2], Double.parseDouble(parts[3]));
    }
}
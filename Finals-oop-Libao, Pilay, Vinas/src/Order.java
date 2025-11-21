import java.util.ArrayList;
import java.util.List;

// Order class with encapsulation
public class Order {
    private int id;
    private int userId;
    private List<Integer> productIds;
    private double total;
    private String date;
    private String status; // e.g., "pending", "confirmed", "shipped"

    public Order(int id, int userId, List<Integer> productIds, double total, String date, String status) {
        this.id = id;
        this.userId = userId;
        this.productIds = productIds;
        this.total = total;
        this.date = date;
        this.status = status;
    }

    public Order(int id, int id1, List<Integer> productIds, double total, String date) {
    }

    // Getters and setters
    public int getId() { return id; }
    public int getUserId() { return userId; }
    public List<Integer> getProductIds() { return productIds; }
    public double getTotal() { return total; }
    public String getDate() { return date; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        return id + "," + userId + "," + String.join(";", productIds.stream().map(String::valueOf).toArray(String[]::new)) + "," + total + "," + date + "," + status;
    }

    // Static method for deserialization
    public static Order fromString(String line) {
        String[] parts = line.split(",");
        List<Integer> pids = new ArrayList<>();
        if (!parts[2].isEmpty()) {
            String[] ids = parts[2].split(";");
            for (String pid : ids) {
                pids.add(Integer.parseInt(pid));
            }
        }
        return new Order(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]), pids, Double.parseDouble(parts[3]), parts[4], parts.length > 5 ? parts[5] : "confirmed");
    }
}
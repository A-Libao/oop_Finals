import java.util.ArrayList;
import java.util.List;

// User class with encapsulation
public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private List<Integer> purchaseHistory; // List of order IDs

    public User(int id, String username, String password, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.purchaseHistory = new ArrayList<>();
    }

    // Getters and setters
    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public List<Integer> getPurchaseHistory() { return purchaseHistory; }

    public void addOrder(int orderId) { purchaseHistory.add(orderId); }

    @Override
    public String toString() {
        return id + "," + username + "," + password + "," + email + "," + String.join(";", purchaseHistory.stream().map(String::valueOf).toArray(String[]::new));
    }

    // Static method for deserialization
    public static User fromString(String line) {
        String[] parts = line.split(",");
        User user = new User(Integer.parseInt(parts[0]), parts[1], parts[2], parts[3]);
        if (parts.length > 4 && !parts[4].isEmpty()) {
            String[] history = parts[4].split(";");
            for (String oid : history) {
                user.addOrder(Integer.parseInt(oid));
            }
        }
        return user;
    }
}
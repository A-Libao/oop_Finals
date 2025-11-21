import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;


// SystemController class: Manages main system flow, data persistence, and GUI interactions
public class SystemController {
    private static final String USERS_FILE = "users.txt";
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String ORDERS_FILE = "orders.txt";
    private static final String LOGINS_FILE = "logins.txt"; // For login tracking
    private List<User> users = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<String> loginLogs = new ArrayList<>(); // For login tracking
    private int nextUserId = 1;
    private int nextProductId = 1;
    private int nextOrderId = 1;
    private DataManager dataManager = DataManager.getInstance();

    public SystemController() {
        loadData();
    }

    private void loadData() {
        dataManager.loadFromFile(USERS_FILE);
        for (Object e : dataManager.getEntities()) {
            if (e instanceof User) {
                users.add((User) e);
                nextUserId = Math.max(nextUserId, ((User) e).getId() + 1);
            }
        }
        dataManager.getEntities().clear();

        dataManager.loadFromFile(PRODUCTS_FILE);
        for (Object e : dataManager.getEntities()) {
            if (e instanceof Product) {
                products.add((Product) e);
                nextProductId = Math.max(nextProductId, ((Product) e).getId() + 1);
            }
        }
        dataManager.getEntities().clear();

        dataManager.loadFromFile(ORDERS_FILE);
        for (Object e : dataManager.getEntities()) {
            if (e instanceof Order) {
                orders.add((Order) e);
                nextOrderId = Math.max(nextOrderId, ((Order) e).getId() + 1);
            }
        }
        dataManager.getEntities().clear();

        // Load login logs
        try (BufferedReader br = new BufferedReader(new FileReader(LOGINS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                loginLogs.add(line);
            }
        } catch (IOException e) {
            // File might not exist
        }
    }

    public void saveData() {
        for (User u : users) dataManager.addEntity(u);
        dataManager.saveToFile(USERS_FILE);
        dataManager.getEntities().clear();

        for (Product p : products) dataManager.addEntity(p);
        dataManager.saveToFile(PRODUCTS_FILE);
        dataManager.getEntities().clear();

        for (Order o : orders) dataManager.addEntity(o);
        dataManager.saveToFile(ORDERS_FILE);
        dataManager.getEntities().clear();

        // Save login logs
        try (PrintWriter pw = new PrintWriter(new FileWriter(LOGINS_FILE))) {
            for (String log : loginLogs) {
                pw.println(log);
            }
        } catch (IOException e) {
            System.out.println("Error saving login logs: " + e.getMessage());
        }
    }

    // GUI support methods
    public User authenticate(String username, String password) {
        for (User u : users) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                // Log login
                String log = username + "," + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
                loginLogs.add(log);
                return u;
            }
        }
        return null;
    }

    public void registerUser(String username, String password, String email) {
        User newUser = new User(nextUserId++, username, password, email);
        users.add(newUser);
    }

    public void addProduct(String name, String desc, double price) {
        Product product = new Product(nextProductId++, name, desc, price);
        products.add(product);
    }

    public void removeProduct(int index) {
        if (index >= 0 && index < products.size()) {
            products.remove(index);
        }
    }

    public void checkout(User user, ShoppingCart cart, boolean confirm) {
        if (user == null || cart == null || cart.getItems().isEmpty()) {
            System.out.println("Error: Invalid user, cart, or empty cart.");
            return;
        }
        String status = confirm ? "confirmed" : "pending";
        List<Integer> productIds = cart.getItems().stream().map(Product::getId).toList();  // Line ~130
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        Order order = new Order(nextOrderId++, user.getId(), productIds, cart.getTotal(), date, status);
        orders.add(order);
        user.addOrder(order.getId());
        if (confirm) cart.clear();
    }

    // Getters for GUI access
    public List<User> getUsers() { return users; }
    public List<Product> getProducts() { return products; }
    public List<Order> getOrders() { return orders; }
    public List<String> getLoginLogs() { return loginLogs; }
    public List<Order> getPendingOrders() {
        return orders.stream().filter(o -> "pending".equals(o.getStatus())).toList();
    }
}
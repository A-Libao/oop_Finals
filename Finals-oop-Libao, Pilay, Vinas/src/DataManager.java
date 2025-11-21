import java.io.*;
import java.util.ArrayList;
import java.util.List;

// DataManager class for handling persistence (Singleton pattern)
public class DataManager {
    private static DataManager instance;
    private List<Object> entities; // Generic list for users, products, orders

    private DataManager() {
        entities = new ArrayList<>();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public void addEntity(Object entity) {
        entities.add(entity);
    }

    public List<Object> getEntities() {
        return entities;
    }

    public void saveToFile(String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Object entity : entities) {
                pw.println(entity.toString());
            }
            System.out.println("Saved to " + filename);
        } catch (IOException e) {
            // Add this for full error details
            System.out.println("Error saving to " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        entities.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                try {
                    if (filename.contains("users")) {
                        entities.add(User.fromString(line));
                    } else if (filename.contains("products")) {
                        entities.add(Product.fromString(line));
                    } else if (filename.contains("orders")) {
                        entities.add(Order.fromString(line));
                    }
                } catch (Exception e) {
                    System.out.println("Error parsing line in " + filename + ": " + line + " - " + e.getMessage());
                    // Skip bad lines
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading " + filename + ": " + e.getMessage());
            // File might not exist, continue
        }
    }
}
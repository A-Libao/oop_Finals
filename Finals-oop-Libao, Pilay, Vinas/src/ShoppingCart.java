import java.util.ArrayList;
import java.util.List;

// ShoppingCart class demonstrating composition and encapsulation
public class ShoppingCart {
    private List<Product> items;

    public ShoppingCart() {
        items = new ArrayList<>();
    }

    public void addProduct(Product product) {
        items.add(product);
    }

    public void removeProduct(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        }
    }

    public List<Product> getItems() {
        return items;
    }

    public double getTotal() {
        return items.stream().mapToDouble(Product::getPrice).sum();
    }

    public void clear() {
        items.clear();
    }
}
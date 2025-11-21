import java.util.List;

// ProductController class: Utility class for product operations
public class ProductController {
    public static void viewProducts(List<Product> products) {
        for (Product p : products) {
            System.out.println("ID: " + p.getId() + ", Name: " + p.getName() + ", Desc: " + p.getDescription() + ", Price: " + p.getPrice());
        }
    }
}
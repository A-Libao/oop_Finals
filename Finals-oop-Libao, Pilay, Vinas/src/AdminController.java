import java.util.List;
import java.util.Scanner;

// AdminController class: Manages admin menu and product operations (kept for potential console fallback, but GUI uses AdminGUI)
public class AdminController {
    private Scanner scanner = new Scanner(System.in);
    private List<Product> products;
    private int nextProductId;

    public AdminController(List<Product> products, int nextProductId) {
        this.products = products;
        this.nextProductId = nextProductId;
    }

    public void start() {
        while (true) {
            System.out.println("Admin Menu");
            System.out.println("1. Add Product");
            System.out.println("2. Remove Product");
            System.out.println("3. View Products");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    removeProduct();
                    break;
                case 3:
                    ProductController.viewProducts(products);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void addProduct() {
        System.out.print("Enter product name: ");
        String name = scanner.nextLine();
        System.out.print("Enter description: ");
        String desc = scanner.nextLine();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        Product product = new Product(nextProductId++, name, desc, price);
        products.add(product);
        System.out.println("Product added successfully!");
    }

    private void removeProduct() {
        ProductController.viewProducts(products);
        System.out.print("Enter product ID to remove: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        products.removeIf(p -> p.getId() == id);
        System.out.println("Product removed successfully!");
    }

    public int getNextProductId() { return nextProductId; }
}
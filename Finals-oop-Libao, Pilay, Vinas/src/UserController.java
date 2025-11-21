import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

// UserController class: Manages user menu, cart, checkout, and history
public class UserController {
    private Scanner scanner = new Scanner(System.in);
    private User user;
    private List<Product> products;
    private List<Order> orders;
    private int nextOrderId;
    private ShoppingCart cart = new ShoppingCart();

    public UserController(User user, List<Product> products, List<Order> orders, int nextOrderId) {
        this.user = user;
        this.products = products;
        this.orders = orders;
        this.nextOrderId = nextOrderId;
    }


    private void browseProducts() {
        ProductController.viewProducts(products);
        System.out.print("Enter product ID to add to cart (0 to go back): ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (id == 0) return;
        Product product = products.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
        if (product != null) {
            cart.addProduct(product);
            System.out.println("Product added to cart.");
        } else {
            System.out.println("Product not found.");
        }
    }

    private void viewCart() {
        List<Product> items = cart.getItems();
        for (int i = 0; i < items.size(); i++) {
            Product p = items.get(i);
            System.out.println((i+1) + ". " + p.getName() + " - $" + p.getPrice());
        }
        System.out.println("Total: $" + cart.getTotal());
        System.out.print("Enter item number to remove (0 to go back): ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index > 0 && index <= items.size()) {
            cart.removeProduct(index - 1);
            System.out.println("Item removed.");
        }
    }

    private void checkout() {
        if (cart.getItems().isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }
        System.out.println("Total: $" + cart.getTotal());
        System.out.print("Confirm payment? (y/n): ");
        String confirm = scanner.nextLine();
        if (confirm.equalsIgnoreCase("y")) {
            // Simulate payment
            System.out.println("Payment successful!");
            // Create order
            List<Integer> productIds = cart.getItems().stream().map(Product::getId).toList();
            String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            Order order = new Order(nextOrderId++, user.getId(), productIds, cart.getTotal(), date);
            orders.add(order);
            user.addOrder(order.getId());
            cart.clear();
            System.out.println("Order placed! Order ID: " + order.getId());
        }
    }

    private void viewHistory() {
        List<Integer> history = user.getPurchaseHistory();
        for (int oid : history) {
            Order order = orders.stream().filter(o -> o.getId() == oid).findFirst().orElse(null);
            if (order != null) {
                System.out.println("Order ID: " + order.getId() + ", Total: $" + order.getTotal() + ", Date: " + order.getDate());
                System.out.println("Products: " + order.getProductIds());
            }
        }
    }

    public int getNextOrderId() { return nextOrderId; }
}
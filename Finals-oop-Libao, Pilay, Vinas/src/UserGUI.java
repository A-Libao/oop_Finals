import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserGUI extends JFrame {
    private SystemController controller;
    private User user;
    private JList<Product> productList;
    private JList<String> cartList, historyList;
    private ShoppingCart cart;

    public UserGUI(SystemController controller, User user) {
        this.controller = controller;
        this.user = user;
        this.cart = new ShoppingCart();
        setTitle("User Panel - " + user.getUsername());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabs for different sections
        JTabbedPane tabbedPane = new JTabbedPane();

        // Browse Products Tab
        JPanel browsePanel = new JPanel(new BorderLayout());
        productList = new JList<>();
        productList.setCellRenderer(new ProductCellRenderer());
        updateProductList();
        browsePanel.add(new JScrollPane(productList), BorderLayout.CENTER);
        JButton addToCartButton = new JButton("Add to Cart");
        addToCartButton.addActionListener(new AddToCartAction());
        browsePanel.add(addToCartButton, BorderLayout.SOUTH);
        tabbedPane.addTab("Browse Products", browsePanel);

        // Cart Tab
        JPanel cartPanel = new JPanel(new BorderLayout());
        cartList = new JList<>();
        updateCartList();
        cartPanel.add(new JScrollPane(cartList), BorderLayout.CENTER);
        JButton removeFromCartButton = new JButton("Remove from Cart");
        removeFromCartButton.addActionListener(new RemoveFromCartAction());
        cartPanel.add(removeFromCartButton, BorderLayout.SOUTH);
        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new CheckoutAction());
        cartPanel.add(checkoutButton, BorderLayout.EAST);
        tabbedPane.addTab("Cart", cartPanel);

        // History Tab
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyList = new JList<>();
        updateHistoryList();
        historyPanel.add(new JScrollPane(historyList), BorderLayout.CENTER);
        tabbedPane.addTab("Purchase History", historyPanel);

        add(tabbedPane, BorderLayout.CENTER);

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            controller.saveData();
            dispose();
            new LoginGUI(controller).setVisible(true);
        });
        add(logoutButton, BorderLayout.SOUTH);
    }

    private void updateProductList() {
        DefaultListModel<Product> model = new DefaultListModel<>();
        for (Product p : controller.getProducts()) {
            model.addElement(p);
        }
        productList.setModel(model);
    }

    private void updateCartList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Product p : cart.getItems()) {
            model.addElement(p.getName() + " - $" + p.getPrice());
        }
        cartList.setModel(model);
    }

    private void updateHistoryList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (int oid : user.getPurchaseHistory()) {
            Order order = controller.getOrders().stream().filter(o -> o.getId() == oid).findFirst().orElse(null);
            if (order != null) {
                model.addElement("Order ID: " + order.getId() + " - $" + order.getTotal() + " - " + order.getDate() + " - Status: " + order.getStatus());
            }
        }
        historyList.setModel(model);
    }

    private class ProductCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Product) {
                Product product = (Product) value;
                // Add null check
                if (product == null) {
                    setText("Product not available");
                    setIcon(null);
                    return this;
                }
                setText("ID: " + product.getId() + " - " + product.getName() + " ($" + product.getPrice() + ") - " + product.getDescription());


            }
            return this;
        }
    }

    private class AddToCartAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Product selected = productList.getSelectedValue();
            if (selected != null) {
                cart.addProduct(selected);
                updateCartList();
                JOptionPane.showMessageDialog(null, "Added to cart!");
            }
        }
    }

    private class RemoveFromCartAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selected = cartList.getSelectedIndex();
            if (selected >= 0) {
                cart.removeProduct(selected);
                updateCartList();
            }
        }
    }

    private class CheckoutAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (cart.getItems().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Cart is empty.");
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(null, "Total: $" + cart.getTotal() + ". Confirm payment?");
            if (confirm == JOptionPane.YES_OPTION) {
                controller.checkout(user, cart, true);
                updateHistoryList();
                cart.clear();
                updateCartList();
                JOptionPane.showMessageDialog(null, "Payment successful! Order placed.");
            }
        }
    }
}
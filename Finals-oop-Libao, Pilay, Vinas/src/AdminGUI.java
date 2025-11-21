import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AdminGUI extends JFrame {
    private SystemController controller;
    private JTextField nameField, descField, priceField;
    private JList<String> productList, pendingOrderList;



    public AdminGUI(SystemController controller) {
        this.controller = controller;
        setTitle("Admin Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabs for products and pending orders
        JTabbedPane tabbedPane = new JTabbedPane();

        // Products Tab
        JPanel productPanel = new JPanel(new BorderLayout());
        productList = new JList<>();
        updateProductList();
        productPanel.add(new JScrollPane(productList), BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Description:"));
        descField = new JTextField();
        inputPanel.add(descField);

        inputPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        inputPanel.add(priceField);


        JButton addButton = new JButton("Add Product");
        addButton.addActionListener(new AddProductAction());
        inputPanel.add(addButton);

        JButton removeButton = new JButton("Remove Product");
        removeButton.addActionListener(new RemoveProductAction());
        inputPanel.add(removeButton);

        productPanel.add(inputPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("Products", productPanel);

        // Pending Orders Tab
        JPanel pendingPanel = new JPanel(new BorderLayout());
        pendingOrderList = new JList<>();
        updatePendingList();
        pendingPanel.add(new JScrollPane(pendingOrderList), BorderLayout.CENTER);

        JButton confirmButton = new JButton("Confirm Order");
        confirmButton.addActionListener(new ConfirmOrderAction());
        pendingPanel.add(confirmButton, BorderLayout.SOUTH);

        tabbedPane.addTab("Pending Orders", pendingPanel);

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
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Product p : controller.getProducts()) {
            model.addElement("ID: " + p.getId() + " - " + p.getName() + " ($" + p.getPrice() + ")");
        }
        productList.setModel(model);
    }

    private void updatePendingList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Order o : controller.getPendingOrders()) {
            model.addElement("Order ID: " + o.getId() + " - User ID: " + o.getUserId() + " - Total: $" + o.getTotal() + " - Date: " + o.getDate());
        }
        pendingOrderList.setModel(model);
    }

    private class AddProductAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String name = nameField.getText();
                String desc = descField.getText();
                double price = Double.parseDouble(priceField.getText());
                controller.addProduct(name, desc, price);
                updateProductList();
                JOptionPane.showMessageDialog(null, "Product added!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid price.");
            }
        }
    }

    private class RemoveProductAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selected = productList.getSelectedIndex();
            if (selected >= 0) {
                controller.removeProduct(selected);
                updateProductList();
                JOptionPane.showMessageDialog(null, "Product removed!");
            } else {
                JOptionPane.showMessageDialog(null, "Select a product to remove.");
            }
        }
    }

    private class ConfirmOrderAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selected = pendingOrderList.getSelectedIndex();
            if (selected >= 0) {
                Order order = controller.getPendingOrders().get(selected);
                order.setStatus("confirmed");
                updatePendingList();
                JOptionPane.showMessageDialog(null, "Order confirmed!");
            } else {
                JOptionPane.showMessageDialog(null, "Select an order to confirm.");
            }
        }
    }
}
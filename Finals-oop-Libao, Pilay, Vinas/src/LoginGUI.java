import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame {
    private SystemController controller;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField emailField;

    public LoginGUI(SystemController controller) {
        this.controller = controller;
        setTitle("Online Shopping System - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginAction());
        add(loginButton);

        JButton registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterAction());
        add(registerButton);

        add(new JLabel("Email (for register):"));
        emailField = new JTextField();
        add(emailField);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> {
            controller.saveData();
            System.exit(0);
        });
        add(exitButton);
    }

    private class LoginAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.equals("admin")) {
                dispose();
                new AdminGUI(controller).setVisible(true);
                return;
            }

            User user = controller.authenticate(username, password);
            if (user != null) {
                dispose();
                new UserGUI(controller, user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials.");
            }
        }
    }

    private class RegisterAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String email = emailField.getText();
            controller.registerUser(username, password, email);
            JOptionPane.showMessageDialog(null, "Registration successful!");
        }
    }
}
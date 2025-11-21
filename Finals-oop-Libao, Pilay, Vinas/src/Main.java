import javax.swing.SwingUtilities;

// Main class: Entry point of the application, launches GUI
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SystemController controller = new SystemController();
            new LoginGUI(controller).setVisible(true);
        });
    }
}
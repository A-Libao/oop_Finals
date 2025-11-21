// Interface for persistable objects
public interface Persistable {
    void saveToFile(String filename);
    void loadFromFile(String filename);
}
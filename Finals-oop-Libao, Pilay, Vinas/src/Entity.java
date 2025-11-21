// Abstract class for entities that can be saved/loaded
public abstract class Entity {
    protected int id;

    public Entity(int id) {
        this.id = id;
    }

    public Entity() {

    }

    public int getId() { return id; }

    // Abstract method for serialization
    public abstract String toString();
}
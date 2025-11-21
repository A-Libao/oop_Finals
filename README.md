1. Encapsulation:

Encapsulation is like creating a secure, self-contained vault for every object. The data inside is private, and the only way to interact with it is through the vault's carefully guarded doors (methods). This protects the data from being tampered with accidentally or maliciously.


- The Guarded Data: In classes like Product.java and User.java, all crucial variables (e.g., product price, user password) are marked private.

- The Controlled Doors (Getters/Setters): You provide public getter and setter methods (e.g., getProductIds(), setName()). If you ever need to add a validation rule (like ensuring a product's price is never negative), you only need to change it in the setPrice() method, and the rest of the application remains unaware of the change.

- The Shopping Cart: The ShoppingCart.java class is a perfect example. The list of items is private. Users don't access the list directly; they use controlled methods like addProduct(), removeProduct(), and getTotal(). This ensures no outside code can corrupt the cart data structure.

2. Abstraction: 
   Abstraction is presenting a simplified "magic button" to the user while hiding the complex machinery that powers it. It focuses on the "What" (the result) rather than the "How" (the implementation).


- The Persistence Contract (Persistable.java): You defined the Persistable interface.

- The "What": Any class that implements this interface promises to fulfill the fundamental duty of persistence: saveToFile(String filename) and loadFromFile(String filename).

- The Hidden "How": The interface doesn't care how the saving is done (comma-separated, database, JSON, etc.). The DataManager.java takes on that complex responsibility, which is completely hidden from the user and the main SystemController. The Controller only sees an object that can save and load.

- The Entity Blueprint (Entity.java): Your abstract Entity class dictates that every data model (like User, Product, Order) must be identifiable with an id and must provide a method to turn itself into a storable string (abstract String toString()). The high-level code only deals with "Entities," not worrying about the specifics of a User vs. an Order.

3. Inheritance:
Inheritance is like a child inheriting traits from a parent. A class (the child) can reuse the structure and behavior of a more general class (the parent), saving effort and enforcing consistency. It creates an "is-a" relationship.


- The Base Class (Entity): Your Entity.java acts as the blueprint for all core data objects.

- The Children: If Product.java and User.java were to explicitly extend Entity (as intended by the abstract class structure):

- Code Reusability: Both Product and User instantly inherit the id field and the getId() method. You don't have to rewrite this basic identity logic for every single data model.

- Hierarchy and Structure: This formalizes the idea that a Product is a type of Entity, and an Order is a type of Entity. This makes it easier for the DataManager to treat them all consistently during file operations.

4. Polymorphism
Polymorphism means "many forms." It allows a single command to execute different actions depending on the type of object receiving the command. It's like having one "Print" button on your computer that prints a Word document, a photo, or a PDF—the action is the same, but the internal process is different for each file type.


- Runtime Method Overriding (toString()): This is your most powerful use of polymorphism.

- The Single Command: Look at the DataManager.saveToFile() method. It iterates over a generic list of Object (or Entity) references and calls entity.toString().

- The Many Forms (Overridden Methods):

- If entity is an Order, the system automatically executes the toString() method in Order.java to serialize the order data (ID, user ID, product IDs, total, status).
- If entity is a Product, the system executes the toString() method in Product.java to serialize the product data (ID, name, description, price).
  
- The DataManager doesn't need to check the object type; it just issues the toString() command, and the object itself knows which specialized version of the method to execute. This makes the persistence logic highly flexible and clean.
  
*Note lang po sir, since this is the only device that we(Libao,Pilay and Vinas) are using po because we are doing the final project offline(together po kame gumagawa in the house of Pilay, Justine po), Thank you po* 



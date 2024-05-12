package model;

/**
 * Represents a product entity with an ID, name, and quantity.
 */
public class Product {
    private int id;
    private String name;
    private int quantity;

    /**
     * Constructs a new Product object with the specified ID, name, and quantity.
     *
     * @param id       The ID of the product.
     * @param name     The name of the product.
     * @param quantity The quantity of the product.
     */
    public Product(int id, String name, int quantity) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Constructs a new Product object with the specified name and quantity.
     *
     * @param name     The name of the product.
     * @param quantity The quantity of the product.
     */
    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    /**
     * Retrieves the ID of the product.
     *
     * @return The ID of the product.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the product.
     *
     * @param id The ID of the product.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the product.
     *
     * @return The name of the product.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the product.
     *
     * @param name The name of the product.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the quantity of the product.
     *
     * @return The quantity of the product.
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product.
     *
     * @param quantity The quantity of the product.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns a string representation of the product.
     *
     * @return A string representation of the product.
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

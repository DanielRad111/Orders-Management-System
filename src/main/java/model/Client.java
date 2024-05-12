package model;

/**
 * Represents a client entity with an ID, name, and email.
 */
public class Client {
    private int id;
    private String name;
    private String email;

    /**
     * Constructs a new Client object with the specified ID, name, and email.
     *
     * @param id    The ID of the client.
     * @param name  The name of the client.
     * @param email The email of the client.
     */
    public Client(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * Constructs a new Client object with the specified name and email.
     *
     * @param name  The name of the client.
     * @param email The email of the client.
     */
    public Client(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /**
     * Retrieves the ID of the client.
     *
     * @return The ID of the client.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the client.
     *
     * @param id The ID of the client.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the client.
     *
     * @return The name of the client.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the client.
     *
     * @param name The name of the client.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the email of the client.
     *
     * @return The email of the client.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the client.
     *
     * @param email The email of the client.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of the client.
     *
     * @return A string representation of the client.
     */
    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", email=" + email + "]";
    }
}

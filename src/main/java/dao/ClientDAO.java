package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import connection.ConnectionFactory;
import model.Client;

/**
 * Data Access Object for managing Client entities in the database.
 */
public class ClientDAO {

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    private static final String insertStatementString = "INSERT INTO client (id, name, email) VALUES (?,?,?)";
    private final static String findStatementString = "SELECT * FROM client where id = ?";
    private final static String findByNameStatementString = "SELECT * FROM client where name = ?";
    private final static String findAllStatementString = "SELECT * FROM client";

    /**
     * Retrieves all clients from the database.
     *
     * @return A list of Client objects representing all clients in the database.
     */
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = findStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                clients.add(new Client(id, name, email));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }

        return clients;
    }

    /**
     * Retrieves a client from the database by its ID.
     *
     * @param id The ID of the client to retrieve.
     * @return The Client object with the specified ID, or null if not found.
     */
    public Client findById(int id) {
        Client toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1, id);
            rs = findStatement.executeQuery();
            rs.next();

            String name = rs.getString("name");
            String email = rs.getString("email");
            toReturn = new Client(id, name, email);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Retrieves a client from the database by its name.
     *
     * @param name The name of the client to retrieve.
     * @return The Client object with the specified name, or null if not found.
     */
    public Client findByName(String name) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        Client toReturn = null;

        try {
            findStatement = dbConnection.prepareStatement(findByNameStatementString);
            findStatement.setString(1, name);
            rs = findStatement.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String email = rs.getString("email");
                toReturn = new Client(id, name, email);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    /**
     * Inserts a new client into the database.
     *
     * @param client The Client object to insert.
     */
    public void insert(Client client) {
        Connection dbConnection = ConnectionFactory.getConnection();

        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString);
            insertStatement.setInt(1, client.getId());
            insertStatement.setString(2, client.getName());
            insertStatement.setString(3, client.getEmail());
            insertStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    /**
     * Updates an existing client in the database.
     *
     * @param client The updated Client object.
     */
    public void update(Client client) {
        System.out.println("ClientDAO: Updating client with ID: " + client.getId());
        System.out.println("New name: " + client.getName() + ", new email: " + client.getEmail());
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        String updateStatementString = "UPDATE client SET name = ?, email = ? WHERE id = ?";

        try {
            updateStatement = dbConnection.prepareStatement(updateStatementString);
            updateStatement.setString(1, client.getName());
            updateStatement.setString(2, client.getEmail());
            updateStatement.setInt(3, client.getId());
            int rowsUpdated = updateStatement.executeUpdate();
            System.out.println("Rows updated: " + rowsUpdated);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:update " + e.getMessage());
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

    /**
     * Deletes a client from the database by its ID.
     *
     * @param id The ID of the client to delete.
     */
    public void delete(int id) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        String deleteStatementString = "DELETE FROM client WHERE id = ?";

        try {
            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
            deleteStatement.setInt(1, id);
            deleteStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
}

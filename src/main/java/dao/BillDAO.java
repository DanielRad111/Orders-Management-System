package dao;

import connection.ConnectionFactory;
import model.Bill;
import model.Client;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object for interacting with the Bill entities in the database.
 */

public class BillDAO extends AbstractDAO<Bill>{

    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());

    private final static String findStatementString = "SELECT * FROM bill where id = ?";
    private final static String findAllStatementString = "SELECT * FROM bill";

    public BillDAO() {

    }
    public List<Bill> findAll() {
        List<Bill> bills = new ArrayList<>();

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findAllStatementString);
            rs = findStatement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String clientName = rs.getString("clientName");
                String productName = rs.getString("productName");
                int quantity = rs.getInt("quantity");
                LocalDate date = rs.getDate("date").toLocalDate();
                bills.add(new Bill(id, clientName, productName, quantity, date));
            }
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }

        return bills;
    }

    public Bill findById(int id) {
        Bill toReturn = null;

        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            findStatement = dbConnection.prepareStatement(findStatementString);
            findStatement.setInt(1, id);
            rs = findStatement.executeQuery();
            rs.next();

            String clientName = rs.getString("clientName");
            String productName = rs.getString("productName");
            int quantity = rs.getInt("quantity");
            LocalDate date = rs.getDate("date").toLocalDate();
            toReturn = new Bill(id, clientName, productName, quantity, date);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "ClientDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }
}

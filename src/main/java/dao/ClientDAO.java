package dao;

import connection.ConnectionFactory;
import model.Client;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO {
    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO client (name, address, email, age)" + " VALUES (?, ?, ?, ?)";
    private final static String findStatementString = "SELECT * FROM client WHERE id = ?";

    public static Client findById(int clientId){
        Client toReturn = null;
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbConnection.prepareStatement(findStatementString);

        }catch (SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO: findById " + e.getMessage());
        }finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return toReturn;
    }

    public static int insert(Client client){
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int insertedId = -1;
        try{
            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
            insertStatement.setString(1, client.getName());
            insertStatement.setString(2, client.getAddress());
            insertStatement.setString(3, client.getEmail());
            insertStatement.setInt(4, client.getAge());
            insertStatement.executeUpdate();

            ResultSet rs = insertStatement.getGeneratedKeys();
            if(rs.next()){
                insertedId = rs.getInt(1);
            }
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, "ClientDAO: insert " + e.getMessage());
        }finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return insertedId;
    }
}

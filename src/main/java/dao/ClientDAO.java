package dao;

import connection.ConnectionFactory;
import model.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientDAO extends AbstractDAO<Client> {
//    protected static final Logger LOGGER = Logger.getLogger(ClientDAO.class.getName());
//    private static final String insertStatementString = "INSERT INTO client (name, email)" + " VALUES (?, ?)";
//    private static final String findStatementString = "SELECT * FROM client WHERE id = ?";
//    private static final String findByNameStatementString = "SELECT * FROM client WHERE name = ?";
//    private static final String findAllStatementString = "SELECT * FROM client";
//
//    @Override
//    public Client findById(int clientId){
//        Client toReturn = null;
//        Connection dbConnection = ConnectionFactory.getConnection();
//        PreparedStatement findStatement = null;
//        ResultSet rs = null;
//        try{
//            findStatement = dbConnection.prepareStatement(findStatementString);
//
//        }catch (SQLException e){
//            LOGGER.log(Level.WARNING, "ClientDAO: findById " + e.getMessage());
//        }finally {
//            ConnectionFactory.close(rs);
//            ConnectionFactory.close(findStatement);
//            ConnectionFactory.close(dbConnection);
//        }
//        return toReturn;
//    }
//
//    @Override
//    public Client findByName(String name){
//        Client toReturn = null;
//        Connection dbConnection = ConnectionFactory.getConnection();
//        PreparedStatement findStatement = null;
//        ResultSet rs = null;
//
//        try {
//            findStatement = dbConnection.prepareStatement(findByNameStatementString);
//            findStatement.setString(1, name);
//            rs = findStatement.getResultSet();
//            while(rs.next()){
//                int id = rs.getInt("id");
//                String email = rs.getString("email");
//                toReturn = new Client(id, name, email);
//            }
//        }catch (SQLException e){
//            LOGGER.log(Level.WARNING, "ClientDAO: findByName " + e.getMessage());
//        }finally {
//            ConnectionFactory.close(rs);
//            ConnectionFactory.close(findStatement);
//            ConnectionFactory.close(dbConnection);
//        }
//
//        return toReturn;
//    }
//
//    @Override
//    public List<Client> findAll() {
//        List<Client> clients = new ArrayList<>();
//        Connection dbConnection = null;
//        PreparedStatement findStatement = null;
//        ResultSet rs = null;
//
//        try {
//            dbConnection = ConnectionFactory.getConnection();
//            findStatement = dbConnection.prepareStatement(findAllStatementString);
//            rs = findStatement.getResultSet();
//            while (rs.next()) {
//                int id = rs.getInt("id");
//                String name = rs.getString("name");
//                String email = rs.getString("email");
//                clients.add(new Client(id, name, email));
//            }
//        } catch (SQLException e) {
//            LOGGER.log(Level.WARNING, "ClientDAO: findAll " + e.getMessage());
//        } finally {
//            ConnectionFactory.close(rs);
//            ConnectionFactory.close(findStatement);
//            ConnectionFactory.close(dbConnection);
//        }
//
//        return clients;
//    }
//
//    @Override
//    public int insert(Client client){
//        Connection dbConnection = ConnectionFactory.getConnection();
//        PreparedStatement insertStatement = null;
//        int insertedId = -1;
//        try{
//            insertStatement = dbConnection.prepareStatement(insertStatementString, Statement.RETURN_GENERATED_KEYS);
//            insertStatement.setString(1, client.getName());
//            insertStatement.setString(2, client.getEmail());
//            insertStatement.executeUpdate();
//
//            ResultSet rs = insertStatement.getGeneratedKeys();
//            if(rs.next()){
//                insertedId = rs.getInt(1);
//            }
//        }catch (SQLException e){
//            LOGGER.log(Level.WARNING, "ClientDAO: insert " + e.getMessage());
//        }finally {
//            ConnectionFactory.close(insertStatement);
//            ConnectionFactory.close(dbConnection);
//        }
//        return insertedId;
//    }
//
//    @Override
//    public void update(Client client){
//        Connection dbConnection = ConnectionFactory.getConnection();
//        PreparedStatement updateStatement = null;
//        String updateStatementString = "UPDATE client SET name = ?, email = ? WHERE id = ?";
//        try{
//            updateStatement = dbConnection.prepareStatement(updateStatementString);
//            updateStatement.setString(1, client.getName());
//            updateStatement.setString(2, client.getEmail());
//            updateStatement.setInt(3, client.getId());
//            updateStatement.executeUpdate();
//        }catch (SQLException e){
//            LOGGER.log(Level.WARNING, "ClientDAO: update " + e.getMessage());
//        }finally {
//            ConnectionFactory.close(updateStatement);
//            ConnectionFactory.close(dbConnection);
//        }
//    }
//
//    @Override
//    public void delete(int id){
//        Connection dbConnection = ConnectionFactory.getConnection();
//        PreparedStatement deleteStatement = null;
//        String deleteStatementString = "DELETE FROM client WHERE id = ?";
//
//        try{
//            deleteStatement = dbConnection.prepareStatement(deleteStatementString);
//            deleteStatement.setInt(1, id);
//            deleteStatement.executeUpdate();
//        }catch (SQLException e){
//            LOGGER.log(Level.WARNING, "ClientDAO: delete " + e.getMessage());
//        }finally {
//            ConnectionFactory.close(deleteStatement);
//            ConnectionFactory.close(dbConnection);
//        }
//    }
    public ClientDAO(){
        
    }
}

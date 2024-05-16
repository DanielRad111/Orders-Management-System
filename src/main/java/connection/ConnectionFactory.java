package connection;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A factory class for managing database connection.
 */
public class ConnectionFactory {
    /**The logger for logging messages. */
    private static final Logger LOGGER = Logger.getLogger(ConnectionFactory.class.getName());
    /** The JDBC driver class name. */
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    /** The database URL. */
    private static final String DBURL = "jdbc:mysql://localhost:3306/Assignment3";
    /** The database username. */
    private static final String USER = "root";
    /** The database password. */
    private static final String PASS = "password";
    /** The single instance of the ConnectionFactory. */
    private static final ConnectionFactory singleInstance = new ConnectionFactory();
    /**
     * Constructs a new ConnectionFactory object.
     * This private constructor ensures that only one instance of ConnectionFactory is created.
     */
    private ConnectionFactory() {
        try {
            Class.forName(DRIVER);
        }catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * Creates a new database connection.
     *
     * @return A connection to the database.
     */
    private Connection createConnection(){
        Connection connection = null;
        try{
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        }catch (SQLException e) {
            LOGGER.log(Level.WARNING, "An error occurred while connecting to the database");
            e.printStackTrace();
        }
        return connection;
    }
    /**
     * Retrieves a database connection.
     *
     * @return A connection to the database.
     */
    public static Connection getConnection(){
        return singleInstance.createConnection();
    }
    /**
     * Closes the given database connection.
     * @param connection The connection to be closed.
     */
    public static void close(Connection connection){
        if(connection != null){
            try{
                connection.close();
            }catch (SQLException e){
                LOGGER.log(Level.WARNING, "An error occurred while closing the connection");
            }
        }
    }
    /**
     * Closes the given database statement.
     *
     * @param statement The statement to be closed.
     */
    public static void close(Statement statement){
        if(statement != null){
            try {
                statement.close();
            }catch (SQLException e){
                LOGGER.log(Level.WARNING, "An error occurred while closing the statement");
            }
        }
    }
    /**
     * Closes the given database result set.
     *
     * @param resultSet The result set to be closed.
     */
    public static void close(ResultSet resultSet){
        if(resultSet != null) {
            try {
                resultSet.close();
            }catch (SQLException e){
                LOGGER.log(Level.WARNING, "An error occurred while closing the resultset");
            }
        }
    }
}

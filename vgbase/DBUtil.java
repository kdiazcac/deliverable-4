package vgbase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Database Utility Class
 * This class provides methods for connecting to the database and executing queries.
 * It handles connection creation, query execution, and resource cleanup.
 * 
 * @author Group 9
 * @version 1.0
 */
public class DBUtil {
    // Database configuration - update these values based on your actual database settings
    private static final String DB_URL = "jdbc:mysql://localhost:3306/vgbase";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
    
    private static Connection connection = null;
    
    /**
     * Gets a connection to the database.
     * If a connection already exists, it returns the existing connection.
     * Otherwise, it creates a new connection.
     * 
     * @return A connection to the database
     * @throws SQLException If a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                // Load the JDBC driver
                Class.forName("com.mysql.cj.jdbc.Driver");
                // Create a connection
                connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (ClassNotFoundException e) {
                throw new SQLException("MySQL JDBC Driver not found", e);
            }
        }
        return connection;
    }
    
    /**
     * Executes a query that doesn't return a result set (e.g., INSERT, UPDATE, DELETE).
     * 
     * @param query The SQL query to execute
     * @return The number of rows affected
     * @throws SQLException If a database access error occurs
     */
    public static int executeUpdate(String query) throws SQLException {
        try (Statement statement = getConnection().createStatement()) {
            return statement.executeUpdate(query);
        }
    }
    
    /**
     * Executes a query that doesn't return a result set using a prepared statement.
     * 
     * @param query The SQL query to execute
     * @param params The parameters for the prepared statement
     * @return The number of rows affected
     * @throws SQLException If a database access error occurs
     */
    public static int executeUpdate(String query, Object... params) throws SQLException {
        try (PreparedStatement pstmt = getConnection().prepareStatement(query)) {
            // Set the parameters
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
            return pstmt.executeUpdate();
        }
    }
    
    /**
     * Executes a query that returns a result set (e.g., SELECT).
     * 
     * @param query The SQL query to execute
     * @return The result set
     * @throws SQLException If a database access error occurs
     */
    public static ResultSet executeQuery(String query) throws SQLException {
        Statement statement = getConnection().createStatement(
            ResultSet.TYPE_SCROLL_INSENSITIVE, 
            ResultSet.CONCUR_READ_ONLY
        );
        return statement.executeQuery(query);
    }
    
    /**
     * Executes a query that returns a result set using a prepared statement.
     * 
     * @param query The SQL query to execute
     * @param params The parameters for the prepared statement
     * @return The result set
     * @throws SQLException If a database access error occurs
     */
    public static ResultSet executeQuery(String query, Object... params) throws SQLException {
        PreparedStatement pstmt = getConnection().prepareStatement(
            query,
            ResultSet.TYPE_SCROLL_INSENSITIVE, 
            ResultSet.CONCUR_READ_ONLY
        );
        
        // Set the parameters
        for (int i = 0; i < params.length; i++) {
            pstmt.setObject(i + 1, params[i]);
        }
        
        return pstmt.executeQuery();
    }
    
    /**
     * Closes the database connection.
     * 
     * @throws SQLException If a database access error occurs
     */
    public static void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            connection = null;
        }
    }
    
    /**
     * Closes a result set and its associated statement.
     * 
     * @param rs The result set to close
     */
    public static void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                Statement stmt = rs.getStatement();
                rs.close();
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
} 
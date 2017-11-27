package uk.ac.bath.se.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Creates, destroys, and holds the connection to the MySQL database.
 *
 * @author Aaron Baker
 */

public class MySQLJDBC {

    /**
     * The database connection.
     */
    private static Connection connection = null;

    /**
     * Creates the connection.
     *
     * @param url      the url of the database, including port.
     * @param uname    the username for the database.
     * @param password the password for the database.
     * @throws ClassNotFoundException thrown if the MySQL JDBC driver is not
     *                                found.
     * @throws SQLException           thrown if there is an issue connecting to the
     *                                database.
     */
    static void createConnection(String url, String uname, String password) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, uname, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the connection to the database.
     *
     * @return the connection to the database.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Closes the connection to the database.
     *
     * @throws SQLException thrown if the connection fails to close.
     */
    public static void close() throws SQLException {
        connection.close();
    }
}

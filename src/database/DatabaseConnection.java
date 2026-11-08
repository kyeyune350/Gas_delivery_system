/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package database;

/**
 *
 * @author DELL LATITUDE 7420
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseConnection {

    // Database information
    private static final String URL =
            "jdbc:mysql://localhost:3306/gas_delivery_system";

    private static final String USERNAME =
            "root";

    private static final String PASSWORD =
            "Ferrarila9";

    // Method for connecting to MySQL
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USERNAME,
                PASSWORD
        );
    }
}

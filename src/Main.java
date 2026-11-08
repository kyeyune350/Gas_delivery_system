
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author DELL LATITUDE 7420
 */
import database.DatabaseConnection;
import java.sql.Connection;

public class Main {

    public static void main(String[] args) {

        try {

            Connection connection =
                    DatabaseConnection.getConnection();

            System.out.println("Database connected successfully.");

            connection.close();

        } catch (Exception e) {

            System.out.println("Database connection failed.");
            System.out.println("Error: " + e.getMessage());

        }
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author DELL LATITUDE 7420
 */
import database.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsersDao{

    // =========================
    // LOGIN
    // =========================
    public static String login(String username, String password) {

        String sql = "SELECT role FROM `user` "
                   + "WHERE username = ? "
                   + "AND password = ? "
                   + "AND status = 'ACTIVE'";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, username);
            statement.setString(2, password);

            try (ResultSet result =
                    statement.executeQuery()) {

                if (result.next()) {
                    return result.getString("role");
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Login error: " + e.getMessage()
            );
        }

        return null;
    }


    // =========================
    // CREATE NEW USER
    // =========================
    public static boolean createUser(
            String name,
            String username,
            String email,
            String phone,
            String password,
            String role,
            String status) {

        String sql =
                "INSERT INTO `user` "
                + "(name, username, email, phone, password, role, status) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (
            Connection connection =
                    DatabaseConnection.getConnection();

            PreparedStatement statement =
                    connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, username);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setString(5, password);
            statement.setString(6, role);
            statement.setString(7, status);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Create user error: " + e.getMessage()
            );

            return false;
        }
    }
}

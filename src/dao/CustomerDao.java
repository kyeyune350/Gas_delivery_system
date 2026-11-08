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
import javax.swing.table.DefaultTableModel;

public class CustomerDao {

    // ADD CUSTOMER
    public static boolean addCustomer(
            String name,
            String phone,
            String email,
            String address) {

        String sql = "INSERT INTO customer "
                + "(name,email,phone, address) "
                + "VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setString(4, address);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add customer error: " + e.getMessage()
            );

            return false;
        }
    }

    // UPDATE CUSTOMER
    public static boolean updateCustomer(
            int customerId,
            String name,
            String phone,
            String email,
            String address) {

        String sql = "UPDATE customer SET "
                + "name = ?, "
                + "phone = ?, "
                + "email = ?, "
                + "address = ? "
                + "WHERE customer_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setString(4, address);
            statement.setInt(5, customerId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update customer error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // DELETE CUSTOMER
    public static boolean deleteCustomer(int customerId) {

        String sql =
                "DELETE FROM customer WHERE customer_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, customerId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Delete customer error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // LOAD ALL CUSTOMERS
    public static void loadCustomers(
            DefaultTableModel model) {

        String sql =
                "SELECT customer_id, name, email, phone, address "
                + "FROM customer "
                + "ORDER BY customer_id";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql);

                ResultSet result =
                        statement.executeQuery()
        ) {

            model.setRowCount(0);

            while (result.next()) {

                model.addRow(new Object[]{
                    result.getInt("customer_id"),
                    result.getString("name"),
                    result.getString("phone"),
                    result.getString("email"),
                    result.getString("address")
                });
            }

        } catch (Exception e) {

            System.out.println(
                    "Load customers error: "
                    + e.getMessage()
            );
        }
    }

    // SEARCH CUSTOMER
public static void searchCustomers(String username, javax.swing.table.DefaultTableModel model) {

    String sql = "SELECT * FROM customer WHERE username = ?";

    try (java.sql.Connection con = DatabaseConnection.getConnection();
         java.sql.PreparedStatement pst = con.prepareStatement(sql)) {

        pst.setString(1, "%" + username + "%");

        try (java.sql.ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                model.addRow(new Object[]{ rs.getInt("customer_id"), rs.getString("username")
                });
            }
        }

    } catch (java.sql.SQLException e) {
        javax.swing.JOptionPane.showMessageDialog(
            null, "Search failed: " + e.getMessage());
    }
}
}

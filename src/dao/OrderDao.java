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

public class OrderDao {

    // ADD ORDER
    public static boolean addOrder(
            int customerId,
            int userId,
            String orderDate,
            double totalAmount,
            String status,
            String deliveryAddress) {

        String sql = "INSERT INTO orders "
                + "(customer_id, user_id, order_date, "
                + "total_amount, status, delivery_address) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, customerId);
            statement.setInt(2, userId);
            statement.setString(3, orderDate);
            statement.setDouble(4, totalAmount);
            statement.setString(5, status);
            statement.setString(6, deliveryAddress);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add order error: " + e.getMessage()
            );

            return false;
        }
    }

    // UPDATE ORDER
    public static boolean updateOrder(
            int orderId,
            int customerId,
            int userId,
            String orderDate,
            double totalAmount,
            String status,
            String deliveryAddress) {

        String sql = "UPDATE orders SET "
                + "customer_id = ?, "
                + "user_id = ?, "
                + "order_date = ?, "
                + "total_amount = ?, "
                + "status = ?, "
                + "delivery_address = ? "
                + "WHERE order_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, customerId);
            statement.setInt(2, userId);
            statement.setString(3, orderDate);
            statement.setDouble(4, totalAmount);
            statement.setString(5, status);
            statement.setString(6, deliveryAddress);
            statement.setInt(7, orderId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update order error: " + e.getMessage()
            );

            return false;
        }
    }

    // DELETE ORDER
    public static boolean deleteOrder(int orderId) {

        String sql =
                "DELETE FROM orders WHERE order_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, orderId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Delete order error: " + e.getMessage()
            );

            return false;
        }
    }

    // LOAD ORDERS
    public static void loadOrders(
            DefaultTableModel model) {

        String sql =
                "SELECT order_id, customer_id, user_id, "
                + "order_date, total_amount, status, "
                + "delivery_address "
                + "FROM orders "
                + "ORDER BY order_id";

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
                    result.getInt("order_id"),
                    result.getInt("customer_id"),
                    result.getInt("user_id"),
                    result.getDate("order_date"),
                    result.getDouble("total_amount"),
                    result.getString("status"),
                    result.getString("delivery_address")
                });
            }

        } catch (Exception e) {

            System.out.println(
                    "Load orders error: " + e.getMessage()
            );
        }
    }

    // SEARCH ORDERS
    public static void searchOrders(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT order_id, customer_id, user_id, "
                + "order_date, total_amount, status, "
                + "delivery_address "
                + "FROM orders "
                + "WHERE CAST(order_id AS CHAR) LIKE ? "
                + "OR CAST(customer_id AS CHAR) LIKE ? "
                + "OR CAST(user_id AS CHAR) LIKE ? "
                + "OR status LIKE ? "
                + "OR delivery_address LIKE ? "
                + "ORDER BY order_id";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            String value = "%" + search + "%";

            statement.setString(1, value);
            statement.setString(2, value);
            statement.setString(3, value);
            statement.setString(4, value);
            statement.setString(5, value);

            try (ResultSet result =
                    statement.executeQuery()) {

                model.setRowCount(0);

                while (result.next()) {

                    model.addRow(new Object[]{
                        result.getInt("order_id"),
                        result.getInt("customer_id"),
                        result.getInt("user_id"),
                        result.getDate("order_date"),
                        result.getDouble("total_amount"),
                        result.getString("status"),
                        result.getString("delivery_address")
                    });
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search order error: " + e.getMessage()
            );
        }
    }
}

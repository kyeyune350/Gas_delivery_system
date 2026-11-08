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

public class OrderItemDao {

    // =========================
    // ADD ORDER ITEM
    // =========================
    public static boolean addOrderItem(
            int orderId,
            int productId,
            int quantity,
            double unitPrice) {

        double subtotal = quantity * unitPrice;

        String sql =
                "INSERT INTO order_items "
                + "(order_id, product_id, quantity, unit_price, subtotal) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            statement.setInt(3, quantity);
            statement.setDouble(4, unitPrice);
            statement.setDouble(5, subtotal);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add order item error: "
                    + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // UPDATE ORDER ITEM
    // =========================
    public static boolean updateOrderItem(
            int orderItemId,
            int orderId,
            int productId,
            int quantity,
            double unitPrice) {

        double subtotal = quantity * unitPrice;

        String sql =
                "UPDATE order_items SET "
                + "order_id = ?, "
                + "product_id = ?, "
                + "quantity = ?, "
                + "unit_price = ?, "
                + "subtotal = ? "
                + "WHERE order_item_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, orderId);
            statement.setInt(2, productId);
            statement.setInt(3, quantity);
            statement.setDouble(4, unitPrice);
            statement.setDouble(5, subtotal);
            statement.setInt(6, orderItemId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update order item error: "
                    + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // DELETE ORDER ITEM
    // =========================
    public static boolean deleteOrderItem(
            int orderItemId) {

        String sql =
                "DELETE FROM order_items "
                + "WHERE order_item_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, orderItemId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Delete order item error: "
                    + e.getMessage()
            );

            return false;
        }
    }


    // =========================
    // LOAD ORDER ITEMS
    // =========================
    public static void loadOrderItems(
            DefaultTableModel model) {

        String sql =
                "SELECT order_item_id, order_id, "
                + "product_id, quantity, unit_price, subtotal "
                + "FROM order_items "
                + "ORDER BY order_item_id";

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
                    result.getInt("order_item_id"),
                    result.getInt("order_id"),
                    result.getInt("product_id"),
                    result.getInt("quantity"),
                    result.getDouble("unit_price"),
                    result.getDouble("subtotal")
                });
            }

        } catch (Exception e) {

            System.out.println(
                    "Load order items error: "
                    + e.getMessage()
            );
        }
    }


    // =========================
    // SEARCH ORDER ITEMS
    // =========================
    public static void searchOrderItems(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT order_item_id, order_id, "
                + "product_id, quantity, unit_price, subtotal "
                + "FROM order_items "
                + "WHERE CAST(order_item_id AS CHAR) LIKE ? "
                + "OR CAST(order_id AS CHAR) LIKE ? "
                + "OR CAST(product_id AS CHAR) LIKE ? "
                + "ORDER BY order_item_id";

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

            try (ResultSet result =
                    statement.executeQuery()) {

                model.setRowCount(0);

                while (result.next()) {

                    model.addRow(new Object[]{
                        result.getInt("order_item_id"),
                        result.getInt("order_id"),
                        result.getInt("product_id"),
                        result.getInt("quantity"),
                        result.getDouble("unit_price"),
                        result.getDouble("subtotal")
                    });
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search order item error: "
                    + e.getMessage()
            );
        }
    }
}

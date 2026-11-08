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

public class InventoryDao {

    // ADD INVENTORY
    public static boolean addInventory(
            int productId,
            int warehouseId,
            int quantity,
            int reorderLevel) {

        String sql = "INSERT INTO inventory "
                + "(product_id, warehouse_id, quantity, reorder_level) "
                + "VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, productId);
            statement.setInt(2, warehouseId);
            statement.setInt(3, quantity);
            statement.setInt(4, reorderLevel);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add inventory error: " + e.getMessage()
            );

            return false;
        }
    }

    // UPDATE INVENTORY
    public static boolean updateInventory(
            int inventoryId,
            int productId,
            int warehouseId,
            int quantity,
            int reorderLevel) {

        String sql = "UPDATE inventory SET "
                + "product_id = ?, "
                + "warehouse_id = ?, "
                + "quantity = ?, "
                + "reorder_level = ? "
                + "WHERE inventory_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, productId);
            statement.setInt(2, warehouseId);
            statement.setInt(3, quantity);
            statement.setInt(4, reorderLevel);
            statement.setInt(5, inventoryId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update inventory error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // DELETE INVENTORY
    public static boolean deleteInventory(
            int inventoryId) {

        String sql =
                "DELETE FROM inventory "
                + "WHERE inventory_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, inventoryId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Delete inventory error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // LOAD INVENTORY
    public static void loadInventory(
            DefaultTableModel model) {

        String sql =
                "SELECT inventory_id, product_id, "
                + "warehouse_id, quantity, reorder_level, "
                + "last_updated "
                + "FROM inventory "
                + "ORDER BY inventory_id";

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
                    result.getInt("inventory_id"),
                    result.getInt("product_id"),
                    result.getInt("warehouse_id"),
                    result.getInt("quantity"),
                    result.getInt("reorder_level"),
                    result.getTimestamp("last_updated")
                });
            }

        } catch (Exception e) {

            System.out.println(
                    "Load inventory error: "
                    + e.getMessage()
            );
        }
    }

    // SEARCH INVENTORY
    public static void searchInventory(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT inventory_id, product_id, "
                + "warehouse_id, quantity, reorder_level, "
                + "last_updated "
                + "FROM inventory "
                + "WHERE CAST(inventory_id AS CHAR) LIKE ? "
                + "OR CAST(product_id AS CHAR) LIKE ? "
                + "OR CAST(warehouse_id AS CHAR) LIKE ? "
                + "ORDER BY inventory_id";

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
                        result.getInt("inventory_id"),
                        result.getInt("product_id"),
                        result.getInt("warehouse_id"),
                        result.getInt("quantity"),
                        result.getInt("reorder_level"),
                        result.getTimestamp("last_updated")
                    });
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search inventory error: "
                    + e.getMessage()
            );
        }
    }
}

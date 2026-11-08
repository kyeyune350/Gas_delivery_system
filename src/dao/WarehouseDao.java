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

public class WarehouseDao {

    // ADD WAREHOUSE
    public static boolean addWarehouse(
            String warehouseName,
            String location,
            int capacity,
            String status) {

        String sql = "INSERT INTO warehouse "
                + "(warehouse_name, location, capacity, status) "
                + "VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, warehouseName);
            statement.setString(2, location);
            statement.setInt(3, capacity);
            statement.setString(4, status);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add warehouse error: " + e.getMessage()
            );

            return false;
        }
    }

    // UPDATE WAREHOUSE
    public static boolean updateWarehouse(
            int warehouseId,
            String warehouseName,
            String location,
            int capacity,
            String status) {

        String sql = "UPDATE warehouse SET "
                + "warehouse_name = ?, "
                + "location = ?, "
                + "capacity = ?, "
                + "status = ? "
                + "WHERE warehouse_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, warehouseName);
            statement.setString(2, location);
            statement.setInt(3, capacity);
            statement.setString(4, status);
            statement.setInt(5, warehouseId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update warehouse error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // DELETE WAREHOUSE
    public static boolean deleteWarehouse(
            int warehouseId) {

        String sql =
                "DELETE FROM warehouse "
                + "WHERE warehouse_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, warehouseId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Delete warehouse error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // LOAD WAREHOUSES
    public static void loadWarehouses(
            DefaultTableModel model) {

        String sql =
                "SELECT warehouse_id, warehouse_name, "
                + "location, capacity, status "
                + "FROM warehouse "
                + "ORDER BY warehouse_id";

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
                    result.getInt("warehouse_id"),
                    result.getString("warehouse_name"),
                    result.getString("location"),
                    result.getInt("capacity"),
                    result.getString("status")
                });
            }

        } catch (Exception e) {

            System.out.println(
                    "Load warehouses error: "
                    + e.getMessage()
            );
        }
    }

    // SEARCH WAREHOUSES
    public static void searchWarehouses(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT warehouse_id, warehouse_name, "
                + "location, capacity, status "
                + "FROM warehouse "
                + "WHERE CAST(warehouse_id AS CHAR) LIKE ? "
                + "OR warehouse_name LIKE ? "
                + "OR location LIKE ? "
                + "OR status LIKE ? "
                + "ORDER BY warehouse_id";

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

            try (ResultSet result =
                    statement.executeQuery()) {

                model.setRowCount(0);

                while (result.next()) {

                    model.addRow(new Object[]{
                        result.getInt("warehouse_id"),
                        result.getString("warehouse_name"),
                        result.getString("location"),
                        result.getInt("capacity"),
                        result.getString("status")
                    });
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search warehouse error: "
                    + e.getMessage()
            );
        }
    }
}

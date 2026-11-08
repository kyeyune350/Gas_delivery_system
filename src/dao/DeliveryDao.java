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

public class DeliveryDao {

    public static boolean addDelivery(
            int orderId,
            int driverId,
            int vehicleId,
            String deliveryAddress,
            String deliveryDate,
            String status) {

        String sql = "INSERT INTO delivery "
                + "(order_id, driver_id, vehicle_id, "
                + "delivery_address, delivery_date, status) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, orderId);
            statement.setInt(2, driverId);
            statement.setInt(3, vehicleId);
            statement.setString(4, deliveryAddress);
            statement.setString(5, deliveryDate);
            statement.setString(6, status);

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(
                    "Add delivery error: " + e.getMessage()
            );
            return false;
        }
    }

    public static boolean updateDelivery(
            int deliveryId,
            int orderId,
            int driverId,
            int vehicleId,
            String deliveryAddress,
            String deliveryDate,
            String status) {

        String sql = "UPDATE delivery SET "
                + "order_id = ?, "
                + "driver_id = ?, "
                + "vehicle_id = ?, "
                + "delivery_address = ?, "
                + "delivery_date = ?, "
                + "status = ? "
                + "WHERE delivery_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, orderId);
            statement.setInt(2, driverId);
            statement.setInt(3, vehicleId);
            statement.setString(4, deliveryAddress);
            statement.setString(5, deliveryDate);
            statement.setString(6, status);
            statement.setInt(7, deliveryId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(
                    "Update delivery error: "
                    + e.getMessage()
            );
            return false;
        }
    }

    public static boolean deleteDelivery(int deliveryId) {

        String sql =
                "DELETE FROM delivery WHERE delivery_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, deliveryId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(
                    "Delete delivery error: "
                    + e.getMessage()
            );
            return false;
        }
    }

    public static void loadDeliveries(
            DefaultTableModel model) {

        String sql =
                "SELECT delivery_id, order_id, driver_id, "
                + "vehicle_id, delivery_address, "
                + "delivery_date, status "
                + "FROM delivery "
                + "ORDER BY delivery_id";

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
                    result.getInt("delivery_id"),
                    result.getInt("order_id"),
                    result.getInt("driver_id"),
                    result.getInt("vehicle_id"),
                    result.getString("delivery_address"),
                    result.getDate("delivery_date"),
                    result.getString("status")
                });
            }

        } catch (Exception e) {
            System.out.println(
                    "Load deliveries error: "
                    + e.getMessage()
            );
        }
    }

    public static void searchDeliveries(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT delivery_id, order_id, driver_id, "
                + "vehicle_id, delivery_address, "
                + "delivery_date, status "
                + "FROM delivery "
                + "WHERE CAST(delivery_id AS CHAR) LIKE ? "
                + "OR CAST(order_id AS CHAR) LIKE ? "
                + "OR CAST(driver_id AS CHAR) LIKE ? "
                + "OR CAST(vehicle_id AS CHAR) LIKE ? "
                + "OR delivery_address LIKE ? "
                + "OR status LIKE ? "
                + "ORDER BY delivery_id";

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
            statement.setString(6, value);

            try (ResultSet result =
                    statement.executeQuery()) {

                model.setRowCount(0);

                while (result.next()) {

                    model.addRow(new Object[]{
                        result.getInt("delivery_id"),
                        result.getInt("order_id"),
                        result.getInt("driver_id"),
                        result.getInt("vehicle_id"),
                        result.getString("delivery_address"),
                        result.getDate("delivery_date"),
                        result.getString("status")
                    });
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Search delivery error: "
                    + e.getMessage()
            );
        }
    }
}

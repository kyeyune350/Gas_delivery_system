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

public class VehicleDao {

    public static boolean addVehicle(
            String registrationNumber,
            String vehicleType,
            int capacity,
            String status) {

        String sql = "INSERT INTO vehicle "
                + "(registration_number, vehicle_type, capacity, status) "
                + "VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, registrationNumber);
            statement.setString(2, vehicleType);
            statement.setInt(3, capacity);
            statement.setString(4, status);

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(
                    "Add vehicle error: " + e.getMessage()
            );
            return false;
        }
    }

    public static boolean updateVehicle(
            int vehicleId,
            String registrationNumber,
            String vehicleType,
            int capacity,
            String status) {

        String sql = "UPDATE vehicle SET "
                + "registration_number = ?, "
                + "vehicle_type = ?, "
                + "capacity = ?, "
                + "status = ? "
                + "WHERE vehicle_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setString(1, registrationNumber);
            statement.setString(2, vehicleType);
            statement.setInt(3, capacity);
            statement.setString(4, status);
            statement.setInt(5, vehicleId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(
                    "Update vehicle error: " + e.getMessage()
            );
            return false;
        }
    }

    public static boolean deleteVehicle(int vehicleId) {

        String sql =
                "DELETE FROM vehicle WHERE vehicle_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setInt(1, vehicleId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(
                    "Delete vehicle error: " + e.getMessage()
            );
            return false;
        }
    }

    public static void loadVehicles(
            DefaultTableModel model) {

        String sql =
                "SELECT vehicle_id, registration_number, "
                + "vehicle_type, capacity, status "
                + "FROM vehicle "
                + "ORDER BY vehicle_id";

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
                    result.getInt("vehicle_id"),
                    result.getString("registration_number"),
                    result.getString("vehicle_type"),
                    result.getInt("capacity"),
                    result.getString("status")
                });
            }

        } catch (Exception e) {
            System.out.println(
                    "Load vehicles error: " + e.getMessage()
            );
        }
    }

    public static void searchVehicles(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT vehicle_id, registration_number, "
                + "vehicle_type, capacity, status "
                + "FROM vehicle "
                + "WHERE CAST(vehicle_id AS CHAR) LIKE ? "
                + "OR registration_number LIKE ? "
                + "OR vehicle_type LIKE ? "
                + "OR status LIKE ? "
                + "ORDER BY vehicle_id";

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
                        result.getInt("vehicle_id"),
                        result.getString("registration_number"),
                        result.getString("vehicle_type"),
                        result.getInt("capacity"),
                        result.getString("status")
                    });
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Search vehicle error: " + e.getMessage()
            );
        }
    }
}
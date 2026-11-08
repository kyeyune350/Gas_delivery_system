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

public class DriverDao {

    // ADD DRIVER
    public static boolean addDriver(
            String name,
            String phone,
            String licenseNumber,
            String status) {

        String sql = "INSERT INTO drivers "
                + "(name, phone, license_number, status) "
                + "VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, licenseNumber);
            statement.setString(4, status);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add driver error: " + e.getMessage()
            );

            return false;
        }
    }

    // UPDATE DRIVER
    public static boolean updateDriver(
            int driverId,
            String name,
            String phone,
            String licenseNumber,
            String status) {

        String sql = "UPDATE drivers SET "
                + "name = ?, "
                + "phone = ?, "
                + "license_number = ?, "
                + "status = ? "
                + "WHERE driver_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, licenseNumber);
            statement.setString(4, status);
            statement.setInt(5, driverId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update driver error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // DELETE DRIVER
    public static boolean deleteDriver(int driverId) {

        String sql =
                "DELETE FROM drivers WHERE driver_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, driverId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Delete driver error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // LOAD ALL DRIVERS
    public static void loadDrivers(
            DefaultTableModel model) {

        String sql =
                "SELECT driver_id, name, phone, "
                + "license_number, status "
                + "FROM drivers "
                + "ORDER BY driver_id";

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
                    result.getInt("driver_id"),
                    result.getString("name"),
                    result.getString("phone"),
                    result.getString("license_number"),
                    result.getString("status")
                });
            }

        } catch (Exception e) {

            System.out.println(
                    "Load drivers error: "
                    + e.getMessage()
            );
        }
    }

    // SEARCH DRIVERS
    public static void searchDrivers(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT driver_id, name, phone, "
                + "license_number, status "
                + "FROM drivers "
                + "WHERE CAST(driver_id AS CHAR) LIKE ? "
                + "OR name LIKE ? "
                + "OR phone LIKE ? "
                + "OR license_number LIKE ? "
                + "OR status LIKE ? "
                + "ORDER BY driver_id";

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
                        result.getInt("driver_id"),
                        result.getString("name"),
                        result.getString("phone"),
                        result.getString("license_number"),
                        result.getString("status")
                    });
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search driver error: "
                    + e.getMessage()
            );
        }
    }
}

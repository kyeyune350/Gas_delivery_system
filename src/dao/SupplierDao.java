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

public class SupplierDao {

    // ADD SUPPLIER
    public static boolean addSupplier(
            String supplierName,
            String phone,
            String email,
            String address) {

        String sql = "INSERT INTO supplier "
                + "(supplier_name, phone, email, address) "
                + "VALUES (?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, supplierName);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setString(4, address);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add supplier error: " + e.getMessage()
            );

            return false;
        }
    }

    // UPDATE SUPPLIER
    public static boolean updateSupplier(
            int supplierId,
            String supplierName,
            String phone,
            String email,
            String address) {

        String sql = "UPDATE supplier SET "
                + "supplier_name = ?, "
                + "phone = ?, "
                + "email = ?, "
                + "address = ? "
                + "WHERE supplier_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, supplierName);
            statement.setString(2, phone);
            statement.setString(3, email);
            statement.setString(4, address);
            statement.setInt(5, supplierId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update supplier error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // DELETE SUPPLIER
    public static boolean deleteSupplier(int supplierId) {

        String sql =
                "DELETE FROM supplier WHERE supplier_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, supplierId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Delete supplier error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // LOAD SUPPLIERS
    public static void loadSuppliers(
            DefaultTableModel model) {

        String sql =
                "SELECT supplier_id, supplier_name, "
                + "phone, email, address "
                + "FROM supplier "
                + "ORDER BY supplier_id";

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
                    result.getInt("supplier_id"),
                    result.getString("supplier_name"),
                    result.getString("phone"),
                    result.getString("email"),
                    result.getString("address")
                });
            }

        } catch (Exception e) {

            System.out.println(
                    "Load suppliers error: "
                    + e.getMessage()
            );
        }
    }

    // SEARCH SUPPLIERS
    public static void searchSuppliers(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT supplier_id, supplier_name, "
                + "phone, email, address "
                + "FROM supplier "
                + "WHERE CAST(supplier_id AS CHAR) LIKE ? "
                + "OR supplier_name LIKE ? "
                + "OR phone LIKE ? "
                + "OR email LIKE ? "
                + "ORDER BY supplier_id";

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
                        result.getInt("supplier_id"),
                        result.getString("supplier_name"),
                        result.getString("phone"),
                        result.getString("email"),
                        result.getString("address")
                    });
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search supplier error: "
                    + e.getMessage()
            );
        }
    }
}

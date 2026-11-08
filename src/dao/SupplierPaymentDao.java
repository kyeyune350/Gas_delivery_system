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

public class SupplierPaymentDao {

    public static boolean addSupplierPayment(
            int supplierId,
            double amount,
            String paymentDate,
            String paymentMethod,
            String referenceNumber) {

        String sql = "INSERT INTO supplier_payment "
                + "(supplier_id, amount, payment_date, "
                + "payment_method, reference_number) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, supplierId);
            statement.setDouble(2, amount);
            statement.setString(3, paymentDate);
            statement.setString(4, paymentMethod);
            statement.setString(5, referenceNumber);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add supplier payment error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public static boolean updateSupplierPayment(
            int supplierPaymentId,
            int supplierId,
            double amount,
            String paymentDate,
            String paymentMethod,
            String referenceNumber) {

        String sql = "UPDATE supplier_payment SET "
                + "supplier_id = ?, "
                + "amount = ?, "
                + "payment_date = ?, "
                + "payment_method = ?, "
                + "reference_number = ? "
                + "WHERE supplier_payment_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, supplierId);
            statement.setDouble(2, amount);
            statement.setString(3, paymentDate);
            statement.setString(4, paymentMethod);
            statement.setString(5, referenceNumber);
            statement.setInt(6, supplierPaymentId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update supplier payment error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public static boolean deleteSupplierPayment(
            int supplierPaymentId) {

        String sql =
                "DELETE FROM supplier_payment "
                + "WHERE supplier_payment_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, supplierPaymentId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {

            System.out.println(
                    "Delete supplier payment error: "
                    + e.getMessage()
            );

            return false;
        }
    }

    public static void loadSupplierPayments(
            DefaultTableModel model) {

        String sql =
                "SELECT supplier_payment_id, supplier_id, "
                + "amount, payment_date, payment_method, "
                + "reference_number "
                + "FROM supplier_payment "
                + "ORDER BY supplier_payment_id";

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
                    result.getInt("supplier_payment_id"),
                    result.getInt("supplier_id"),
                    result.getDouble("amount"),
                    result.getDate("payment_date"),
                    result.getString("payment_method"),
                    result.getString("reference_number")
                });
            }

        } catch (Exception e) {

            System.out.println(
                    "Load supplier payments error: "
                    + e.getMessage()
            );
        }
    }

    public static void searchSupplierPayments(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT supplier_payment_id, supplier_id, "
                + "amount, payment_date, payment_method, "
                + "reference_number "
                + "FROM supplier_payment "
                + "WHERE CAST(supplier_payment_id AS CHAR) LIKE ? "
                + "OR CAST(supplier_id AS CHAR) LIKE ? "
                + "OR payment_method LIKE ? "
                + "OR reference_number LIKE ? "
                + "ORDER BY supplier_payment_id";

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
                        result.getInt("supplier_payment_id"),
                        result.getInt("supplier_id"),
                        result.getDouble("amount"),
                        result.getDate("payment_date"),
                        result.getString("payment_method"),
                        result.getString("reference_number")
                    });
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search supplier payment error: "
                    + e.getMessage()
            );
        }
    }
}
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

public class PaymentDao {

    public static boolean addPayment(
            int orderId,
            double amount,
            String paymentDate,
            String paymentMethod,
            String status,
            String referenceNumber) {

        String sql = "INSERT INTO payment "
                + "(order_id, amount, payment_date, "
                + "payment_method, status, reference_number) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setInt(1, orderId);
            statement.setDouble(2, amount);
            statement.setString(3, paymentDate);
            statement.setString(4, paymentMethod);
            statement.setString(5, status);
            statement.setString(6, referenceNumber);

            statement.executeUpdate();
            return true;

        } catch (Exception e) {
            System.out.println(
                    "Add payment error: " + e.getMessage()
            );
            return false;
        }
    }

    public static boolean updatePayment(
            int paymentId,
            int orderId,
            double amount,
            String paymentDate,
            String paymentMethod,
            String status,
            String referenceNumber) {

        String sql = "UPDATE payment SET "
                + "order_id = ?, "
                + "amount = ?, "
                + "payment_date = ?, "
                + "payment_method = ?, "
                + "status = ?, "
                + "reference_number = ? "
                + "WHERE payment_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setInt(1, orderId);
            statement.setDouble(2, amount);
            statement.setString(3, paymentDate);
            statement.setString(4, paymentMethod);
            statement.setString(5, status);
            statement.setString(6, referenceNumber);
            statement.setInt(7, paymentId);

            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(
                    "Update payment error: " + e.getMessage()
            );
            return false;
        }
    }

    public static boolean deletePayment(int paymentId) {

        String sql =
                "DELETE FROM payment WHERE payment_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setInt(1, paymentId);
            return statement.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println(
                    "Delete payment error: " + e.getMessage()
            );
            return false;
        }
    }

    public static void loadPayments(
            DefaultTableModel model) {

        String sql =
                "SELECT payment_id, order_id, amount, "
                + "payment_date, payment_method, status, "
                + "reference_number "
                + "FROM payment "
                + "ORDER BY payment_id";

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
                    result.getInt("payment_id"),
                    result.getInt("order_id"),
                    result.getDouble("amount"),
                    result.getDate("payment_date"),
                    result.getString("payment_method"),
                    result.getString("status"),
                    result.getString("reference_number")
                });
            }

        } catch (Exception e) {
            System.out.println(
                    "Load payments error: " + e.getMessage()
            );
        }
    }

    public static void searchPayments(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT payment_id, order_id, amount, "
                + "payment_date, payment_method, status, "
                + "reference_number "
                + "FROM payment "
                + "WHERE CAST(payment_id AS CHAR) LIKE ? "
                + "OR CAST(order_id AS CHAR) LIKE ? "
                + "OR payment_method LIKE ? "
                + "OR status LIKE ? "
                + "OR reference_number LIKE ? "
                + "ORDER BY payment_id";

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
                        result.getInt("payment_id"),
                        result.getInt("order_id"),
                        result.getDouble("amount"),
                        result.getDate("payment_date"),
                        result.getString("payment_method"),
                        result.getString("status"),
                        result.getString("reference_number")
                    });
                }
            }

        } catch (Exception e) {
            System.out.println(
                    "Search payment error: " + e.getMessage()
            );
        }
    }
}
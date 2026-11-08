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

public class ProductDao {

    // ADD PRODUCT
    public static boolean addProduct(
            String productName,
            String description,
            double unitPrice,
            String gasType,
            String cylinderSize) {

        String sql = "INSERT INTO gas_products "
                + "(product_name, description, unit_price, gas_type, cylinder_size) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, productName);
            statement.setString(2, description);
            statement.setDouble(3, unitPrice);
            statement.setString(4, gasType);
            statement.setString(5, cylinderSize);

            statement.executeUpdate();

            return true;

        } catch (Exception e) {

            System.out.println(
                    "Add product error: " + e.getMessage()
            );

            return false;
        }
    }

    // UPDATE PRODUCT
    public static boolean updateProduct(
            int productId,
            String productName,
            String description,
            double unitPrice,
            String gasType,
            String cylinderSize) {

        String sql = "UPDATE gas_products SET "
                + "product_name = ?, "
                + "description = ?, "
                + "unit_price = ?, "
                + "gas_type = ?, "
                + "cylinder_size = ? "
                + "WHERE product_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setString(1, productName);
            statement.setString(2, description);
            statement.setDouble(3, unitPrice);
            statement.setString(4, gasType);
            statement.setString(5, cylinderSize);
            statement.setInt(6, productId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Update product error: " + e.getMessage()
            );

            return false;
        }
    }

    // DELETE PRODUCT
    public static boolean deleteProduct(int productId) {

        String sql =
                "DELETE FROM gas_products WHERE product_id = ?";

        try (
                Connection connection =
                        DatabaseConnection.getConnection();

                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {

            statement.setInt(1, productId);

            int rows = statement.executeUpdate();

            return rows > 0;

        } catch (Exception e) {

            System.out.println(
                    "Delete product error: " + e.getMessage()
            );

            return false;
        }
    }

    // LOAD ALL PRODUCTS
    public static void loadProducts(
            DefaultTableModel model) {

        String sql =
                "SELECT product_id, product_name, description, "
                + "unit_price, gas_type, cylinder_size "
                + "FROM gas_products "
                + "ORDER BY product_id";

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
                    result.getInt("product_id"),
                    result.getString("product_name"),
                    result.getString("description"),
                    result.getDouble("unit_price"),
                    result.getString("gas_type"),
                    result.getString("cylinder_size")
                });
            }

        } catch (Exception e) {

            System.out.println(
                    "Load products error: " + e.getMessage()
            );
        }
    }

    // SEARCH PRODUCTS
    public static void searchProducts(
            DefaultTableModel model,
            String search) {

        String sql =
                "SELECT product_id, product_name, description, "
                + "unit_price, gas_type, cylinder_size "
                + "FROM gas_products "
                + "WHERE CAST(product_id AS CHAR) LIKE ? "
                + "OR product_name LIKE ? "
                + "OR gas_type LIKE ? "
                + "OR cylinder_size LIKE ? "
                + "ORDER BY product_id";

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
                        result.getInt("product_id"),
                        result.getString("product_name"),
                        result.getString("description"),
                        result.getDouble("unit_price"),
                        result.getString("gas_type"),
                        result.getString("cylinder_size")
                    });
                }
            }

        } catch (Exception e) {

            System.out.println(
                    "Search product error: " + e.getMessage()
            );
        }
    }
}

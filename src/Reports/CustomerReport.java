/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Reports;

/**
 *
 * @author DELL LATITUDE 7420
 */
import database.DatabaseConnection;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

public class CustomerReport {

    public static void generateCustomerReport() {

        String fileName = "CustomerReport.pdf";
        String sql = "SELECT customer_id, name, phone, email, address "
                   + "FROM customer ORDER BY customer_id";

        Document document = new Document(PageSize.A4.rotate());
        try {
            PdfWriter.getInstance(document,new FileOutputStream(fileName) );
            document.open();
            Font titleFont = new Font(
                    Font.FontFamily.HELVETICA,
                    18,
                    Font.BOLD
            );

            Font headerFont = new Font(
                    Font.FontFamily.HELVETICA,
                    10,
                    Font.BOLD
            );
            Paragraph title = new Paragraph("GAS DELIVERY MANAGEMENT SYSTEM",titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            Paragraph subtitle = new Paragraph("CUSTOMER REPORT",new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD));
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);
            document.add(new Paragraph(" "));
            String date = new SimpleDateFormat(
                    "dd-MM-yyyy HH:mm"
            ).format(new Date());
            document.add(new Paragraph("Report generated: " + date));
            document.add(new Paragraph(" "));
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1.2f, 2.5f, 2.0f, 3.0f, 3.0f});
            String[] headers = {
                "Customer ID", "Name", "Phone", "Email", "Address"
            };
            for (String header : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(
                        header, headerFont
                ));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(7);
                table.addCell(cell);
            }
            int count = 0;
            try (
                Connection connection =DatabaseConnection.getConnection();
                PreparedStatement statement =connection.prepareStatement(sql);
                ResultSet result = statement.executeQuery()
            ) {
                while (result.next()) {
                    table.addCell(String.valueOf(
                            result.getInt("customer_id")
                    ));

                    table.addCell(result.getString("name"));
                    table.addCell(result.getString("phone"));
                    table.addCell(result.getString("email"));
                    table.addCell(result.getString("address"));

                    count++;
                }
            }
            if (count == 0) {
                PdfPCell emptyCell = new PdfPCell(new Phrase("No customer records found."));
                emptyCell.setColspan(5);
                emptyCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                emptyCell.setPadding(10);
                table.addCell(emptyCell);
            }
            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total customers: " + count));
            document.close();
            File pdfFile = new File(fileName);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(pdfFile);
            } else {JOptionPane.showMessageDialog(null,"Report saved at: " + pdfFile.getAbsolutePath());
            }
        } catch (Exception e) {
            if (document.isOpen()) {
                document.close();
            }
            JOptionPane.showMessageDialog(null,"Could not generate customer report:\n"+ e.getMessage(),"Report Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
           }
     public static void main(String[] args){
        generateCustomerReport();
     }
}
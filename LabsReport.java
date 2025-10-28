/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Admin
 */
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LabsReport {
     public static void main(String[] args) {
        // PDF file path
        String pdfPath = "LabsReport.pdf";

        // Database connection info
        String url = "jdbc:mysql://localhost:3306/hospitalms";
        String user = "root";  // replace with your DB user
        String password = "";  // replace with your DB password

        try {
            // Create a new PDF document
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, new FileOutputStream(pdfPath));
            document.open();
            
             //Logo
            Image logo = Image.getInstance("pill.jpg");
        logo.scaleToFit(100, 50);
        logo.setAlignment(Image.ALIGN_RIGHT);
        document.add(logo);
        
        // system Info
        Font companyFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
        Font infoFont = FontFactory.getFont(FontFactory.HELVETICA, 11);

        Paragraph companyName = new Paragraph("MEDICORE HMS", companyFont);
        companyName.setAlignment(Element.ALIGN_LEFT);

        Paragraph address = new Paragraph("P.O. Box 1467, Mbarara, Uganda", infoFont);
        address.setAlignment(Element.ALIGN_LEFT);

        Paragraph timestamp = new Paragraph("Generated on: " + new java.text.SimpleDateFormat("dd MMMM yyyy, HH:mm:ss").format(new java.util.Date()), infoFont);
        timestamp.setAlignment(Element.ALIGN_LEFT);

        document.add(companyName);
        document.add(address);
        document.add(timestamp);
        document.add(Chunk.NEWLINE);

            // Add title
            document.add(new Paragraph("Lab Tests Report\n\n"));

            // Create a table with 6 columns
            PdfPTable table = new PdfPTable(6);
            table.addCell("Test ID");
            table.addCell("Patient ID");
            table.addCell("Doctor ID");
            table.addCell("Test Type");
            table.addCell("Results");
            table.addCell("Test Date");

            // Connect to the database
            Connection con = DriverManager.getConnection(url, user, password);
            Statement stmt = con.createStatement();
            String query = "SELECT * FROM lab_tests";
            ResultSet rs = stmt.executeQuery(query);

            // Populate table with data
            while (rs.next()) {
                table.addCell(String.valueOf(rs.getInt("test_id")));
                table.addCell(String.valueOf(rs.getInt("patient_id")));
                table.addCell(String.valueOf(rs.getInt("doctor_id")));
                table.addCell(rs.getString("test_type"));
                table.addCell(rs.getString("results"));
                table.addCell(rs.getString("test_date"));
            }

            // Add table to document
            document.add(table);
            document.close();

            System.out.println("Lab tests report generated successfully: " + pdfPath);

        } catch (DocumentException de) {
            de.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}

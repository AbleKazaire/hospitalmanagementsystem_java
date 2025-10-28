package reports;
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
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PharmacyReport {
    public void createPdf() throws DocumentException, IOException {
    String RESULT = "PharmacyReport.pdf";  // file name

        Document document = new Document(PageSize.A4);
        try {
            // Step 1: Create PDF writer
            PdfWriter.getInstance(document, new FileOutputStream(RESULT));
            document.open();
            
            //Logo
            Image logo = Image.getInstance("stetho.jpg");
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

            // Step 2: Add title
            document.add(new Paragraph("Hospital Management System - Pharmacy Report\n\n"));

            // Step 3: Create table with 2 columns (since only drug_id & name exist)
            PdfPTable table = new PdfPTable(2);
            table.addCell("Drug ID");
            table.addCell("Name");

            // Step 4: Fetch data from DB
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospitalms", "root", "");
                 PreparedStatement pst = conn.prepareStatement("SELECT drug_id, name FROM pharmacy");
                 ResultSet rs = pst.executeQuery()) {

                while (rs.next()) {
                    table.addCell(String.valueOf(rs.getInt("drug_id")));
                    table.addCell(rs.getString("name"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            // Step 5: Add table to PDF
            document.add(table);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        // Step 6: Auto-open the PDF
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(RESULT);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            new PharmacyReport().createPdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

        
    }


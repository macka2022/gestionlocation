package com.example.springbootexamgestionlocationimmeuble.DAO.implemeentDAO;

import com.example.springbootexamgestionlocationimmeuble.DAO.RapportService;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class ReportImplementService implements RapportService {

    private final AdminDashboardService dashboardService;

    public ReportImplementService(AdminDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @Override
    public ByteArrayInputStream generatePdfReport() {
        var vm = dashboardService.getSnapshot();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document doc = new Document();
            PdfWriter.getInstance(doc, out);
            doc.open();

            doc.add(new Paragraph("Rapport de Gestion des Locations", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18)));
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph("Nombre de contrats en cours : " + vm.activeContracts));
            doc.add(new Paragraph("Revenus générés (YTD) : " + vm.ytdRevenue + " FCFA"));
            doc.add(new Paragraph("Loyers impayés : " + vm.unpaidCount));

            doc.close();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération du PDF", e);
        }
    }

    @Override
    public ByteArrayInputStream generateExcelReport() {
        var vm = dashboardService.getSnapshot();

        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet("Rapport");

            // Header
            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Indicateur");
            header.createCell(1).setCellValue("Valeur");

            // Données
            sheet.createRow(1).createCell(0).setCellValue("Contrats en cours");
            sheet.getRow(1).createCell(1).setCellValue(vm.activeContracts);

            sheet.createRow(2).createCell(0).setCellValue("Revenus générés (YTD)");
            sheet.getRow(2).createCell(1).setCellValue(vm.ytdRevenue.doubleValue());

            sheet.createRow(3).createCell(0).setCellValue("Loyers impayés");
            sheet.getRow(3).createCell(1).setCellValue(vm.unpaidCount);

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la génération de l’Excel", e);
        }
    }
}

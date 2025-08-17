package com.example.springbootexamgestionlocationimmeuble.DAO;

import java.io.ByteArrayInputStream;

public interface RapportService {
    public ByteArrayInputStream generatePdfReport();
    public ByteArrayInputStream generateExcelReport();
}

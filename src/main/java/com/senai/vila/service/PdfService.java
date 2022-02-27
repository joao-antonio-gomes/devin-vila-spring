package com.senai.vila.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.opencsv.*;
import com.senai.vila.model.dto.FinancialReportDto;
import lombok.SneakyThrows;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PdfService {

    @SneakyThrows
    public static String createPdfFinancialReport(FinancialReportDto financialReportDto) {
        String[] header = {"Id", "Saldo Remanescente", "Saldo Gasto", "Habitante Mais Caro", "Relatório Emitido em"};
        String[] data = {financialReportDto.getId().toString(),
                financialReportDto.getBudgetRemaining().toString(),
                financialReportDto.getBudgetSpent().toString(),
                financialReportDto.getMostExpensiveResident().getCpf(),
                financialReportDto.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))};
        String horaAgora = LocalDateTime.now().toString();

        List<String[]> dataList = new ArrayList<>();
        dataList.add(header);
        dataList.add(data);

        String fileName = "RelatorioFinanceiro_" + horaAgora;
        try (ICSVWriter writer = new CSVWriterBuilder(new FileWriter(fileName + ".csv")).withSeparator(';').build()) {
            writer.writeAll(dataList);
        }

        //csv reader with separator

        CSVReader reader = new CSVReaderBuilder(new FileReader(fileName + ".csv")).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();
        Document my_pdf_data = new Document();
        PdfWriter.getInstance(my_pdf_data, new FileOutputStream(fileName + ".pdf"));
        my_pdf_data.open();
        PdfPTable my_table = new PdfPTable(header.length);
        PdfPCell table_cell;
        for (String[] row : reader.readAll()) {
            for (String cell : row) {
                table_cell = new PdfPCell(new Phrase(cell));
                my_table.addCell(table_cell);
            }
        }

        my_pdf_data.add(my_table);
        my_pdf_data.close();
        new File(fileName + ".csv").delete();
        return fileName;
    }
}

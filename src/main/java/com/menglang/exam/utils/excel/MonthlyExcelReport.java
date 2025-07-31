package com.menglang.exam.utils.excel;

import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.exam.dto.result.ReportResponse;
import com.menglang.exam.service.reports.monthly.MonthlyReport;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class MonthlyExcelReport implements ExcelReport{
    private final MonthlyReport monthlyReport;

    @Override
    public void exportReportToExcel(Long examId, Long classroomId, Long academicYear, HttpServletResponse response){
        List<ReportResponse> reportResponses=monthlyReport.getMonthlyReport(examId,classroomId,academicYear);

        // Use SXSSFWorkbook for streaming
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100)) {
           Sheet sheet = workbook.createSheet("Monthly Reports");

           Row headerRow = sheet.createRow(0);
           int colIndex = 0;

           headerRow.createCell(colIndex++).setCellValue("Rank");
           headerRow.createCell(colIndex++).setCellValue("Student Id");
           headerRow.createCell(colIndex++).setCellValue("Student Name");
           headerRow.createCell(colIndex++).setCellValue("Total Score");
           headerRow.createCell(colIndex++).setCellValue("Average Score");

            // Dynamic subject columns
            Set<String> allSubjects = reportResponses.stream()
                    .flatMap(r -> r.getSubjectScores().keySet().stream())
                    .collect(TreeSet::new, Set::add, Set::addAll);

            Map<String, Integer> subjectColumnIndex = new HashMap<>();
            for (String subject : allSubjects) {
                headerRow.createCell(colIndex).setCellValue(subject);
                subjectColumnIndex.put(subject, colIndex++);
            }

           int rowIndex = 1;
           for (ReportResponse report : reportResponses) {
               Row row = sheet.createRow(rowIndex++);
               int cellIndex = 0;

               row.createCell(cellIndex++).setCellValue(report.getRank());
               row.createCell(cellIndex++).setCellValue(report.getStudentId());
               row.createCell(cellIndex++).setCellValue(report.getStudentName());
               row.createCell(cellIndex++).setCellValue(report.getTotalScore());
               row.createCell(cellIndex++).setCellValue(report.getTotalAverage());
               for (Map.Entry<String, Double> entry : report.getSubjectScores().entrySet()) {
                   int idx = subjectColumnIndex.get(entry.getKey());
                   row.createCell(idx).setCellValue(entry.getValue());
               }
           }

           response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
           response.setHeader("Content-Disposition", "attachment; filename=monthly-report.xlsx");

           //stream workbook direct to client (10000 rows up)
           try (ServletOutputStream outputStream = response.getOutputStream()) {
               workbook.write(outputStream);
               outputStream.flush();
           }

       }catch (IOException e){
           log.warn(e.getLocalizedMessage());
           throw new BadRequestException("Failed to Export Excel Report.");
       }

    }

}

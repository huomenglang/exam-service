package com.menglang.exam.controller;

import com.menglang.common.library.page.PageResponse;
import com.menglang.common.library.page.PageResponseHandler;
import com.menglang.exam.dto.result.ReportResponse;
import com.menglang.exam.service.reports.monthly.MonthlyReportService;
import com.menglang.exam.utils.excel.ExcelReport;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reports-exam")
@RequiredArgsConstructor
public class ReportController {
    private final MonthlyReportService monthlyReportService;
    private final ExcelReport excelReport;

    @GetMapping("/monthly")
    public ResponseEntity<PageResponse> getMonthlyReport(
            @RequestParam Long examId,
            @RequestParam Long classroomId,
            @RequestParam Long academicYearId
    ){
        List<ReportResponse> monthlyReports=monthlyReportService.getMonthlyReport(examId,classroomId,academicYearId);
        return PageResponseHandler.success(monthlyReports,null,"Get Monthly Report Success");
    }

    @GetMapping("/download-report-monthly")
    public void downloadReportMonthlyAsExcel(
            @RequestParam Long examId,
            @RequestParam Long classroomId,
            @RequestParam Long academicYearId,
            HttpServletResponse response
    ){
        excelReport.exportReportToExcel(examId,classroomId,academicYearId,response);
    }
}

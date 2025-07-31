package com.menglang.exam.service.reports.monthly;

import com.menglang.exam.dto.result.ReportResponse;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface MonthlyReportService {
    List<ReportResponse> getMonthlyReport(Long exam_id,Long classroom_id,Long academicYearId);
    List<ReportResponse> getBaseMonthlyReport(Long exam_id,Long classroom_id,Long academicYearId);

}

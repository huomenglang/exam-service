package com.menglang.exam.utils.excel;

import jakarta.servlet.http.HttpServletResponse;

public interface ExcelReport {
    void exportReportToExcel(Long examId, Long classroomId, Long academicYear, HttpServletResponse response);
}

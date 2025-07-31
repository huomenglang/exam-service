package com.menglang.exam.dto.result.monthly;

public record MonthlyAttendanceResponse(
        Long studentId,
        String studentName,
        int totalAttendant,
        Double averageScore,
        int rank
) {

}

package com.menglang.exam.dto.result.monthly;

public record MonthlyResponse(
        Long studentId,
        String studentName,
        Long subjectId,
        String subject,
        Double averageScore
) {

}

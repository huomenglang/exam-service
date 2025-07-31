package com.menglang.exam.dto.result.monthly;

public record MonthlyBaseResponse(
        Long studentId,
        Long subjectId,
        Short score,
        Double averageScore
) {

}

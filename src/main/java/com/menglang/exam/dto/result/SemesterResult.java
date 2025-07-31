package com.menglang.exam.dto.result;

public record SemesterResult(
        Long studentId,
        Long subjectId,
        Double averageScore
) {
}

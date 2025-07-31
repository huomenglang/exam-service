package com.menglang.exam.dto.result;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
@Setter
public class ReportResponse {
    private Long studentId;
    private String studentName;
    private Map<String, Double> subjectScores;
    private double totalScore;
    private double totalAverage;
    private int rank;
}


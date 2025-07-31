package com.menglang.exam.service.reports.monthly;

import com.menglang.exam.dto.result.ReportResponse;
import com.menglang.exam.dto.result.monthly.MonthlyBaseResponse;
import com.menglang.exam.dto.student.StudentResponse;
import com.menglang.exam.dto.subject.SubjectResponse;
import com.menglang.exam.repository.ScoreExamRepository;
import com.menglang.exam.service.student.StudentService;
import com.menglang.exam.service.subject.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MonthlyReport implements MonthlyReportService{
    private final ScoreExamRepository scoreExamRepository;
    private final StudentService studentService;
    private final SubjectService subjectService;

    @Override
    public List<ReportResponse> getMonthlyReport(Long examId, Long classroomId, Long academicYearId) {
        // Step 1: Fetch base data
        List<MonthlyBaseResponse> baseReports = scoreExamRepository.findMonthlyBaseReport(examId, classroomId, academicYearId);

        // Step 2: Extract unique studentIds and subjectIds
        Set<Long> studentIds = baseReports.stream()
                .map(MonthlyBaseResponse::studentId)
                .collect(Collectors.toSet());

        Set<Long> subjectIds = baseReports.stream()
                .map(MonthlyBaseResponse::subjectId)
                .collect(Collectors.toSet());

        // Step 3: Fetch related data asynchronously
        CompletableFuture<List<StudentResponse>> studentFuture = studentService.getStudentsByIds(new ArrayList<>(studentIds));
        CompletableFuture<List<SubjectResponse>> subjectFuture = subjectService.getStudentByIds(new ArrayList<>(subjectIds));

        CompletableFuture.allOf(studentFuture, subjectFuture).join();

        List<StudentResponse> studentResponses = studentFuture.join();
        List<SubjectResponse> subjectResponses = subjectFuture.join();

        // Step 4: Map responses for quick access
        Map<Long, StudentResponse> studentMap = studentResponses.stream()
                .collect(Collectors.toMap(StudentResponse::id, Function.identity()));

        Map<Long, SubjectResponse> subjectMap = subjectResponses.stream()
                .collect(Collectors.toMap(SubjectResponse::id, Function.identity()));

        // Step 5: Group base reports by studentId
        Map<Long, List<MonthlyBaseResponse>> groupedByStudent = baseReports.stream()
                .collect(Collectors.groupingBy(MonthlyBaseResponse::studentId));

        // Step 6: Build final report
        List<ReportResponse> reports = new ArrayList<>();

        for (Map.Entry<Long, List<MonthlyBaseResponse>> entry : groupedByStudent.entrySet()) {
            Long studentId = entry.getKey();
            List<MonthlyBaseResponse> studentReports = entry.getValue();

            Map<String, Double> subjectScores = new HashMap<>();
            double totalScore = 0;

            for (MonthlyBaseResponse row : studentReports) {
                SubjectResponse subject = subjectMap.get(row.subjectId());
                if (subject != null) {
                    subjectScores.put(subject.name(), row.averageScore());
                }
                totalScore += row.score();
            }

            double average = subjectScores.isEmpty() ? 0 : totalScore / subjectScores.size();
            StudentResponse student = studentMap.get(studentId);

            String fullName = student != null ? student.firstName() + " " + student.lastName() : "Unknown";

            ReportResponse report = ReportResponse.builder()
                    .studentId(studentId)
                    .studentName(fullName)
                    .subjectScores(subjectScores)
                    .totalScore(totalScore)
                    .totalAverage(average)
                    .build();
            reports.add(report);
        }

        // Sort and assign rank
      return rankSorting(reports);
    }


    @Override
    public List<ReportResponse> getBaseMonthlyReport(Long exam_id, Long classroom_id, Long academicYearId) {
        return List.of();
    }

    private List<ReportResponse> rankSorting(List<ReportResponse> reports){
        // Sort descending by totalScore
        reports.sort(Comparator.comparingDouble(ReportResponse::getTotalScore).reversed());

        // Assign rank with tie support
        int rank = 1;
        for (int i = 0; i < reports.size(); i++) {
            if (i > 0 && reports.get(i).getTotalScore() < reports.get(i - 1).getTotalScore()) {
                rank = i + 1;
            }
            reports.get(i).setRank(rank);
        }
        return reports;
    }
}

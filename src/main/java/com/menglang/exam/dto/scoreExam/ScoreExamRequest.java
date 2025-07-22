package com.menglang.exam.dto.scoreExam;
import jakarta.validation.constraints.*;
import java.io.Serializable;

public record ScoreExamRequest(
        @NotNull(message = "Student must not be null")
        Long studentId,
        @NotNull(message = "Subject must not be null")
        Long subjectId,
        @NotNull(message = "Classroom must not be null")
        Long classroomId,
        @NotNull(message = "Academic Year must not be null")
        Long academicYearId,
        @NotNull(message = "Exam must not be null")
        Long examId,
        @NotNull(message = "Teacher must not be null")
        Long teacherId,
        @NotNull(message = "Score must not be null")
        @Positive(message = "Score must be positive")
        @Max(value = 100, message = "Score must be at most 100")
        Short score,
        String description
) implements Serializable {
}

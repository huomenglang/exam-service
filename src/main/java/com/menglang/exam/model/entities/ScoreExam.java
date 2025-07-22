package com.menglang.exam.model.entities;
import com.menglang.exam.model.audit.AuditEntity;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(
        name = "score_exam",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_score_exam_student_exam_subject_year",
                        columnNames = {"student_id", "exam_id", "subject_id", "academic_year_id"}
                )
        },
        indexes = @Index(name = "idx_score_exam_query", columnList = "student_id, exam_id")
)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScoreExam extends AuditEntity<Long> implements Serializable {

    @Column(name = "student_id")
    private Long student;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Column(name = "classroom_id")
    private Long classroom;

    @Column(name = "subject_id")
    private Long subject;

    @Column(name = "academic_year_id")
    private Long academicYear;

    @Column(name = "teacher_id")
    private Long teacher;

    private Short score;

    @Column(length = 200)
    private String description;

}

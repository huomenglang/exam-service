package com.menglang.exam.model.entities;
import com.menglang.exam.model.audit.AuditEntity;
import com.menglang.exam.model.enums.ScoreType;
import jakarta.persistence.*;
import lombok.*;


@Table(name = "score_exams")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ScoreExam extends AuditEntity<Long> {

    @Column(name = "student_id")
    private Long studentId;

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

    @Column(name = "score_type",length = 15)
    @Enumerated(EnumType.STRING)
    private ScoreType scoreType;

    @Column(length = 200)
    private String description;

}

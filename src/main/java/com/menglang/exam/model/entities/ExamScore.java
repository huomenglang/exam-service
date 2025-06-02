package com.menglang.exam.model.entities;

import com.menglang.exam.model.audit.AuditEntity;
import com.menglang.exam.model.enums.ScoreType;
import jakarta.persistence.*;
import lombok.*;


@Table(name = "exam_score")
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExamScore extends AuditEntity<Long> {

    @Column(name = "student_id")
    private Long studentId;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    @Column(name = "classroom_id")
    private Long classroomId;

    @Column(name = "academic_year_id")
    private Long academicYearId;

    private Short score;

    @Column(name = "score_type",length = 15)
    @Enumerated(EnumType.STRING)
    private ScoreType scoreType;

}

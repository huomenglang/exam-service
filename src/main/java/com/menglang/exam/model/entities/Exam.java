package com.menglang.exam.model.entities;

import com.menglang.exam.model.audit.AuditEntity;
import com.menglang.exam.model.audit.BaseEntity;
import com.menglang.exam.model.enums.ExamType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Table(name = "exam")
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Exam extends AuditEntity<Long>{
    @Column(length = 50)
    private String name;

    @Column(length = 150)
    private String description;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ExamType examType;

    @Column(length = 10,name = "academic_year_id")
    private Long academicYearId;

}

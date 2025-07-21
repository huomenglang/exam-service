package com.menglang.exam.model.entities;

import com.menglang.exam.model.audit.AuditEntity;
import com.menglang.exam.model.enums.ExamType;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Table(name = "exams")
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Exam extends AuditEntity<Long> implements Serializable {
    @Column(length = 50)
    private String name;

    @Column(length = 150)
    private String description;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private ExamType examType;

}

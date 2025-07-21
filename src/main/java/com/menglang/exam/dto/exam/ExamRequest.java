package com.menglang.exam.dto.exam;

import com.menglang.exam.model.enums.ExamType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public record ExamRequest(
        @NotBlank(message = "Name must not be null")
        @Size(message = "Length of Name must be between 3 char and 60 char", min = 3, max = 60)
        String name,
        @NotNull(message = "Type of Exam must not be null")
        ExamType examType,
        String description,
        Boolean enabled

) implements Serializable {
}

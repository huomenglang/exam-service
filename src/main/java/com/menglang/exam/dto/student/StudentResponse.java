package com.menglang.exam.dto.student;

import com.menglang.exam.model.enums.Gender;
public record StudentResponse(
        Long id,
        String firstName,
        String lastName,
        Gender gender,
        String phoneNumber

) {
}

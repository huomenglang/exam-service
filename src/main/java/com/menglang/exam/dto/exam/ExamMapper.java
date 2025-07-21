package com.menglang.exam.dto.exam;

import com.menglang.exam.model.entities.Exam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExamMapper {

    Exam toEntity(ExamRequest request);
    ExamResponse toResponse(Exam entity);

    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "createdBy",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "updatedBy",ignore = true)
    @Mapping(target = "enabled",ignore = true)
    void examUpdate(ExamRequest request, @MappingTarget Exam entity);
}

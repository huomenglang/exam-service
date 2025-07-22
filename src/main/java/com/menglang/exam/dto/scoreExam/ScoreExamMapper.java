package com.menglang.exam.dto.scoreExam;

import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.exam.dto.academicYear.AcademicYearResponse;
import com.menglang.exam.dto.classroom.ClassroomResponse;
import com.menglang.exam.dto.student.StudentResponse;
import com.menglang.exam.dto.subject.SubjectResponse;
import com.menglang.exam.dto.teacher.TeacherResponse;
import com.menglang.exam.model.entities.Exam;
import com.menglang.exam.model.entities.ScoreExam;
import com.menglang.exam.model.enums.Gender;
import com.menglang.exam.repository.ExamRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ScoreExamMapper {
    @Autowired
    protected ExamRepository examRepository;

    public void ScoreExamMapper(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Mapping(target = "exam",expression = "java(toExam(request.examId()))")
    @Mapping(target = "academicYear",source = "academicYearId")
    @Mapping(target = "teacher",source = "teacherId")
    @Mapping(target = "subject",source = "subjectId")
    @Mapping(target = "classroom",source = "classroomId")
    @Mapping(target = "student",source = "studentId")
    public abstract ScoreExam toEntity(ScoreExamRequest request);


    public abstract List<ScoreExam> toBatchEntity(List<ScoreExamRequest> requests);


    @Mapping(target = "academicYear",expression = "java(toAcademicYear(res.getAcademicYear()))")
    @Mapping(target = "teacher",expression = "java(toTeacher(res.getTeacher()))")
    @Mapping(target = "subject",expression = "java(toSubject(res.getSubject()))")
    @Mapping(target = "classroom",expression = "java(toClassroom(res.getClassroom()))")
    @Mapping(target = "student",expression = "java(toStudent(res.getStudent()))")
    public abstract ScoreExamResponse toResponse(ScoreExam res);




    @Mapping(target = "id",ignore = true)
    @Mapping(target = "createdAt",ignore = true)
    @Mapping(target = "createdBy",ignore = true)
    @Mapping(target = "updatedAt",ignore = true)
    @Mapping(target = "updatedBy",ignore = true)
    @Mapping(target = "enabled",ignore = true)
    @Mapping(target = "exam",expression = "java(toExam(request.examId()))")
    @Mapping(target = "academicYear",source = "academicYearId")
    @Mapping(target = "teacher",source = "teacherId")
    @Mapping(target = "subject",source = "subjectId")
    @Mapping(target = "classroom",source = "classroomId")
    @Mapping(target = "student",source = "studentId")
    public abstract void updateScoreExam(ScoreExamRequest request,@MappingTarget ScoreExam entity);

    @Named("toExam")
    protected Exam toExam(Long examId){
        return examRepository.findById(examId).orElseThrow(()->new NotFoundException("Exam Not Found"));
    }

    @Named("toAcademicYear")
    protected AcademicYearResponse toAcademicYear(Long id){
        return new AcademicYearResponse(id,"2025-2026");
    }

    @Named("toTeacher")
    protected TeacherResponse toTeacher(Long id){
        return new TeacherResponse(id,"Menglout");
    }

    @Named("toSubject")
    protected SubjectResponse toSubject(Long id){
        return new SubjectResponse(id,"Math");
    }

    @Named("toClassroom")
    protected ClassroomResponse toClassroom(Long id){
        return new ClassroomResponse(id,"Grade 11A");
    }

    @Named("toStudent")
    protected StudentResponse toStudent(Long id){
        return new StudentResponse(id,"Menglang","Huo", Gender.MALE,"012 454545");
    }
}

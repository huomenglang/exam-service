package com.menglang.exam.dto.scoreExam;

import com.menglang.common.library.page.paginate.BasePageResponse;
import com.menglang.exam.dto.academicYear.AcademicYearResponse;
import com.menglang.exam.dto.classroom.ClassroomResponse;
import com.menglang.exam.dto.exam.ExamResponse;
import com.menglang.exam.dto.student.StudentResponse;
import com.menglang.exam.dto.subject.SubjectResponse;
import com.menglang.exam.dto.teacher.TeacherResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ScoreExamResponse extends BasePageResponse implements Serializable {
    private Long id;
    private Byte score;
    private StudentResponse student;
    private ExamResponse exam;
    private ClassroomResponse classroom;
    private SubjectResponse subject;
    private TeacherResponse teacher;
    private AcademicYearResponse academicYear;
    private String description;
    private Boolean enabled;
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDateTime updatedAt;
    private String updatedBy;

}

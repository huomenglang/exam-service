package com.menglang.exam.repository;

import com.menglang.exam.dto.result.monthly.MonthlyBaseResponse;
import com.menglang.exam.dto.result.monthly.MonthlyResponse;
import com.menglang.exam.model.entities.ScoreExam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScoreExamRepository extends JpaRepository<ScoreExam,Long>, JpaSpecificationExecutor<ScoreExam> {


    @Query(value = """
    SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
    FROM score_exam
    WHERE student_id = :studentId
      AND exam_id = :examId
      AND subject_id = :subjectId
      AND academic_year_id = :academicYearId
      AND id <> :id
    """, nativeQuery = true)
    boolean existsDuplicateOnUpdate(
            @Param("studentId") Long studentId,
            @Param("examId") Long examId,
            @Param("subjectId") Long subjectId,
            @Param("academicYearId") Long academicYearId,
            @Param("id") Long id
    );



    @Query(value = """
    SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END
    FROM score_exam
    WHERE student_id = :studentId
      AND exam_id = :examId
      AND subject_id = :subjectId
      AND academic_year_id = :academicYearId
    """, nativeQuery = true)
    boolean existsDuplicate(
            @Param("studentId") Long studentId,
            @Param("examId") Long examId,
            @Param("subjectId") Long subjectId,
            @Param("academicYearId") Long academicYearId
    );


    // Batch check (returns existing duplicates)
    @Query(value = """
        SELECT s.student_id, s.exam_id, s.subject_id, s.academic_year_id
        FROM score_exam s
        WHERE (s.student_id, s.exam_id, s.subject_id, s.academic_year_id)
        IN (:keys)
        """, nativeQuery = true)
    List<Object[]> findExistingDuplicates(@Param("keys") List<Object[]> keys);


    // monthly reports for one database all services
    @Query(value = """
            SELECT
                se.student_id as studentId,
                st.name as studentName,
                sub.name as subject,
                AVG(se.score) as averageScore
             FROM score_exam se
                JOIN exam e ON se.exam_id=e.id
                JOIN student st ON se.student_id=st.id
                JOIN subject sub ON se.subject_id=sub.id
             WHERE
                exam_id=:examId
                AND classroom_id=:classroomId
                AND academic_year_id=:academicYearId""",nativeQuery = true)
    List<MonthlyResponse>  findMonthlyReport(
            @Param("examId") int examId,
            @Param("classroomId") int classroomId,
            @Param("academicYearId") int academicYearId
    );

    //monthly reports one database per service
    @Query(value = """
            SELECT
                s.student_id AS studentId,
                s.subject_id AS subjectId,
                s.score AS score,
                AVG(s.score) AS averageScore
             FROM score_exam s
             WHERE
                exam_id=:examId
                AND classroom_id=:classroomId
                AND academic_year_id=:academicYearId""",nativeQuery = true)
    List<MonthlyBaseResponse>  findMonthlyBaseReport(
            @Param("examId") Long examId,
            @Param("classroomId") Long classroomId,
            @Param("academicYearId") Long academicYearId
    );

}

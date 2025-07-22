package com.menglang.exam.repository;

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
    List<Object[]> findExistingKeys(@Param("keys") List<Object[]> keys);


}

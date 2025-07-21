package com.menglang.exam.repository;

import com.menglang.exam.model.entities.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long>, JpaSpecificationExecutor<Exam> {

    @Query("""
            SELECT CASE WHEN COUNT(e)>0 THEN true ELSE false END e FROM Exam e WHERE e.name=?1 and e.id !=?2
            """)
    Boolean findByNameAndIdNotEqual(String name,Long id);
}

package com.menglang.exam.service.seeding;

import com.menglang.exam.dto.exam.ExamMapper;
import com.menglang.exam.dto.exam.ExamRequest;
import com.menglang.exam.dto.scoreExam.ScoreExamMapper;
import com.menglang.exam.dto.scoreExam.ScoreExamRequest;
import com.menglang.exam.model.entities.Exam;
import com.menglang.exam.model.entities.ScoreExam;
import com.menglang.exam.model.enums.ExamType;
import com.menglang.exam.repository.ExamRepository;
import com.menglang.exam.repository.ScoreExamRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;

@Component
@RequiredArgsConstructor
public class SeedData {

    private static final Logger log = LoggerFactory.getLogger(SeedData.class);
    private final ExamRepository examRepository;
    private final ScoreExamRepository scoreExamRepository;
    private final ExamMapper examMapper;
    private final ScoreExamMapper scoreExamMapper;

    @PostConstruct
    private void seeding(){
        seedExam();
        seedScoreExam();
    }

    private void seedExam(){
        ExamRequest e1=new ExamRequest("January", ExamType.MONTHLY,"January",true);
        ExamRequest e2=new ExamRequest("February", ExamType.MONTHLY,"February",true);
        ExamRequest e3=new ExamRequest("February", ExamType.MONTHLY,"February",true);
        ExamRequest e5=new ExamRequest("April", ExamType.MONTHLY,"April",true);
        ExamRequest e4=new ExamRequest("Semester I", ExamType.SEMESTER1,"Semester I",true);
        ExamRequest e6=new ExamRequest("May", ExamType.MONTHLY,"May",true);
        ExamRequest e7=new ExamRequest("June", ExamType.MONTHLY,"June",true);
        ExamRequest e8=new ExamRequest("July", ExamType.MONTHLY,"July",true);
        ExamRequest e9=new ExamRequest("August", ExamType.MONTHLY,"August",true);
        ExamRequest e11=new ExamRequest("September", ExamType.MONTHLY,"September",true);
        ExamRequest e10=new ExamRequest("Semester II", ExamType.SEMESTER2,"Semester II",true);
        List<ExamRequest> examRequests=List.of(e1,e2,e3,e4,e5,e6,e7,e8,e9,e10,e11);
        List<Exam> examList=examRequests.stream().map(examMapper::toEntity).toList();
        log.info("Seeding Exam ..................................");
        examRepository.saveAll(examList);
    }
    private void seedScoreExam(){
        log.info("Seeding Score Exam ..................................");
        List<ScoreExamRequest> scoreExamRequestList=new ArrayList<>();
        for (int i=1;i<=10;i++){
            Long studentId= (long) i;
            for (int j=1;j<=10;j++){
                Long subjectId= (long) j;
                Long teacherId=(long) j;
                for (int k=1;k<=4;k++){
                    Long examId=(long) k;
                    Random random=new Random();
                    short score= (short) random.nextInt(100);
                    var scoreExam=new ScoreExamRequest(studentId,subjectId,1L,1L,examId,teacherId,score,"exam...");
                    scoreExamRequestList.add(scoreExam);
                }
            }
        }
        List<ScoreExam> scoreExams=scoreExamRequestList.stream().map(scoreExamMapper::toEntity).toList();
        scoreExamRepository.saveAll(scoreExams);
        log.info("Finish Seeding.................");
    }
}

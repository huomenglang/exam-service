package com.menglang.exam.service.scoreExam;

import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.common.library.exceptions.common.ConflictException;
import com.menglang.common.library.exceptions.common.DuplicateRequestException;
import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.common.library.page.filter.FilterBy;
import com.menglang.common.library.page.parser.BaseSpecification;
import com.menglang.common.library.page.parser.PageableParser;
import com.menglang.common.library.page.parser.QueryParamParser;
import com.menglang.exam.dto.scoreExam.ScoreExamMapper;
import com.menglang.exam.dto.scoreExam.ScoreExamRequest;
import com.menglang.exam.dto.scoreExam.ScoreExamResponse;
import com.menglang.exam.model.entities.ScoreExam;
import com.menglang.exam.repository.ScoreExamRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ScoreExamServiceImpl implements ScoreExamService{
    private static final Logger log = LoggerFactory.getLogger(ScoreExamServiceImpl.class);
    private final ScoreExamRepository scoreExamRepository;
    private final ScoreExamMapper scoreExamMapper;


    @Override
    public ScoreExamResponse create(ScoreExamRequest scoreExam) {
        log.info("score input: {}",scoreExam.score());
        if (findDuplicateScoreStudent(scoreExam)) {
            throw new DuplicateRequestException("Duplicate score entry of Student");
        }
        ScoreExam scoreExamEntity=scoreExamMapper.toEntity(scoreExam);
        try{
               return scoreExamMapper.toResponse(scoreExamRepository.save(scoreExamEntity));
        }catch (BadRequestException e){
            log.info("Error Create Score: {}",e.getMessage());
            throw new BadRequestException("Error Create Score Exam");
        }catch (RuntimeException e){
            log.info("Error Create Base Score: {}",e.getMessage());
            throw new BadRequestException("Error Create Score Exam");
        }

    }

    @Override
    public ScoreExamResponse update(Long id, ScoreExamRequest dto) {
        ScoreExam scoreExam=findScoreExamById(id);

        if(scoreExamRepository.existsDuplicateOnUpdate(dto.studentId(), dto.examId(),dto.subjectId(),dto.academicYearId(),id)){
            throw new DuplicateRequestException("Duplicate score entry of Student");
        }
        scoreExamMapper.updateScoreExam(dto,scoreExam);
        try{
            return scoreExamMapper.toResponse(scoreExamRepository.save(scoreExam));
        }catch (BadRequestException e){
            log.info("Error Update Score: {}",e.getMessage());
            throw new BadRequestException("Error Update Score Exam");
        }catch (RuntimeException e){
            log.info("Error Update Base Score: {}",e.getMessage());
            throw new BadRequestException("Error Update Score Exam");
        }

    }

    @Override
    public ScoreExamResponse delete(Long id) {
        ScoreExam scoreExam=this.findScoreExamById(id);
        try{
            scoreExamRepository.deleteById(id);
            return scoreExamMapper.toResponse(scoreExam);
        }catch (BadRequestException e){
            log.info("Error Delete Score: {}",e.getMessage());
            throw new BadRequestException("Error Delete Score Exam");
        }catch (RuntimeException e){
            log.info("Error Delete Base Score: {}",e.getMessage());
            throw new BadRequestException("Error Delete Score Exam");
        }


    }

    @Override
    public ScoreExamResponse getById(Long id) {
        ScoreExam scoreExam=this.findScoreExamById(id);
        return scoreExamMapper.toResponse(scoreExam);
    }

    @Override
    public List<ScoreExamResponse> getPageContent(Page<ScoreExam> data) {
        return data.getContent().stream().map(scoreExamMapper::toResponse).toList();
    }

    @Override
    public Page<ScoreExam> getAll(Map<String, String> params) {
        Pageable pageable= PageableParser.from(params);
        List<FilterBy> filterByList= QueryParamParser.parse(params);
        Specification<ScoreExam> spec=new BaseSpecification<>(filterByList);
        return scoreExamRepository.findAll(spec,pageable);
    }

    private boolean findDuplicateScoreStudent(ScoreExamRequest scoreExam){

        boolean count= scoreExamRepository.existsDuplicate(
                scoreExam.studentId(),
                scoreExam.examId(),
                scoreExam.subjectId(),
                scoreExam.academicYearId()
        );
        log.info("Is duplicate: {}",count);
        return count;
    }

    @Override
    public List<ScoreExamResponse> saveAllScoreExams(List<ScoreExamRequest> scoreExamRequestList) {


        // Prepare tuples for batch check
        List<Object[]> pairs = scoreExamRequestList.stream()
                .map(se -> new Object[]{
                        se.subjectId(),
                        se.examId(),
                        se.subjectId(),
                        se.subjectId()
                })
                .toList();

        // Check duplicates in 1 query
//        List<Object[]> existingDuplicates = scoreExamRepository.findExistingDuplicates(pairs);
        List<Object[]>existingDuplicates=scoreExamRepository.findExistingDuplicates(pairs);
        if (!existingDuplicates.isEmpty()) {
            throw new ConflictException("Duplicates found: " + existingDuplicates);
        }
        List<ScoreExam> scoreExamList=scoreExamMapper.toBatchEntity(scoreExamRequestList);
        try{
            List<ScoreExam> scoreExams= scoreExamRepository.saveAll(scoreExamList);
            return scoreExams.stream().map(scoreExamMapper::toResponse).toList();

        }catch (BadRequestException e){
            log.info("Error Create batch Score exam: {}",e.getMessage());
            throw new BadRequestException("Error Create Score Exam");
        }catch (RuntimeException e){
            log.info("Error Create Base Batch Score Exam: {}",e.getMessage());
            throw new BadRequestException("Error Create Batch Score Exams");
        }
    }

    private ScoreExam findScoreExamById(Long id){
        return scoreExamRepository.findById(id).orElseThrow(()->new NotFoundException("Score of Exam Not Found!"));
    }
}

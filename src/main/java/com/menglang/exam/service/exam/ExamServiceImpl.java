package com.menglang.exam.service.exam;
import com.menglang.common.library.exceptions.common.BadRequestException;
import com.menglang.common.library.exceptions.common.ConflictException;
import com.menglang.common.library.exceptions.common.NotFoundException;
import com.menglang.common.library.page.filter.FilterBy;
import com.menglang.common.library.page.parser.BaseSpecification;
import com.menglang.common.library.page.parser.PageableParser;
import com.menglang.common.library.page.parser.QueryParamParser;
import com.menglang.exam.dto.exam.ExamMapper;
import com.menglang.exam.dto.exam.ExamRequest;
import com.menglang.exam.dto.exam.ExamResponse;
import com.menglang.exam.model.entities.Exam;
import com.menglang.exam.repository.ExamRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
@Service
public class ExamServiceImpl implements ExamService{
    private static final Logger log = LoggerFactory.getLogger(ExamServiceImpl.class);
    private final ExamRepository examRepository;
    private final ExamMapper examMapper;

    @Override
    public ExamResponse create(ExamRequest dto) {
        if (findDuplicateExam(0L, dto.name())) throw new ConflictException("Exam Already Exist!");
        Exam exam=examMapper.toEntity(dto);

        try{
            return examMapper.toResponse(examRepository.save(exam));
        }catch (BadRequestException e){
            log.warn("Cannot create Exam {}",e.getMessage());
            throw new BadRequestException("Cannot create Exam!");
        }catch (RuntimeException e){
            log.warn("Cannot create Exam Runtime {}",e.getMessage());
            throw new BadRequestException("Cannot create Exam!");
        }

    }

    @Override
    public ExamResponse update(Long id, ExamRequest dto) {
        if (findDuplicateExam(id, dto.name())) throw new ConflictException("Exam Already Exist!");
        Exam exam=this.findExamById(id);
        examMapper.examUpdate(dto,exam);
        try{
            return examMapper.toResponse(examRepository.save(exam));
        }catch (BadRequestException e){
            log.warn("Cannot update Exam {}",e.getMessage());
            throw new BadRequestException("Cannot create Exam!");
        }catch (RuntimeException e){
            log.warn("Cannot update Exam Runtime {}",e.getMessage());
            throw new BadRequestException("Cannot create Exam!");
        }
    }

    @Override
    public ExamResponse delete(Long id) {
        Exam exam=this.findExamById(id);
        try{
            this.examRepository.deleteById(id);
            return examMapper.toResponse(exam);
        }catch (BadRequestException e){
            log.warn("Cannot update Exam {}",e.getMessage());
            throw new BadRequestException("Cannot create Exam!");
        }catch (RuntimeException e){
            log.warn("Cannot update Exam Runtime {}",e.getMessage());
            throw new BadRequestException("Cannot create Exam!");
        }
    }

    @Override
    public ExamResponse getById(Long id) {
        Exam exam=this.findExamById(id);
        return examMapper.toResponse(exam);
    }

    @Override
    public List<ExamResponse> getPageContent(Page<Exam> data) {
        return data.getContent().stream().map(examMapper::toResponse).toList();
    }

    @Override
    public Page<Exam> getAll(Map<String, String> params) {
        Pageable pageable= PageableParser.from(params);
        List<FilterBy> filterByList= QueryParamParser.parse(params);
        Specification<Exam> spec=new BaseSpecification<>(filterByList);
        return examRepository.findAll(spec,pageable);
    }

    @Override
    public ExamResponse updateStatus(Long id, String enabled) {
        Exam exam=this.findExamById(id);
        if (!enabled.equalsIgnoreCase("true") && !enabled.equalsIgnoreCase("false")) throw new BadRequestException("Incorrect Enabled Value");
        exam.setEnabled(Boolean.valueOf(enabled));
        try{
            return examMapper.toResponse(examRepository.save(exam));
        }catch (BadRequestException e){
            log.warn("Cannot update Exam {}",e.getMessage());
            throw new BadRequestException("Cannot create Exam!");
        }catch (RuntimeException e){
            log.warn("Cannot update Exam Runtime {}",e.getMessage());
            throw new BadRequestException("Cannot create Exam!");
        }
    }
    private Exam findExamById(Long id){
        return examRepository.findById(id).orElseThrow(()-> new NotFoundException("Exam Not Found!"));
    }

    private boolean findDuplicateExam(Long id,String name){
        return examRepository.findByNameAndIdNotEqual(name, id);
    }
}

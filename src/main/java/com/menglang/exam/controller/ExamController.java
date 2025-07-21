package com.menglang.exam.controller;

import com.menglang.common.library.page.PageResponse;
import com.menglang.common.library.page.PageResponseHandler;
import com.menglang.exam.dto.exam.ExamRequest;
import com.menglang.exam.dto.exam.ExamResponse;
import com.menglang.exam.model.entities.Exam;
import com.menglang.exam.service.exam.ExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/exams")
@RequiredArgsConstructor
public class ExamController {
    private final ExamService examService;

    @PostMapping
    public ResponseEntity<PageResponse> create(@Valid @RequestBody ExamRequest request){
        return PageResponseHandler.success(examService.create(request),null,"Create Success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageResponse> getById(@PathVariable Long id){
        return PageResponseHandler.success(examService.getById(id),null,"Get Success");
    }

    @GetMapping("get-all")
    public ResponseEntity<PageResponse> getAll(Map<String,String> params){
        Page<Exam> examPage=examService.getAll(params);
        List<ExamResponse> examResponses=examService.getPageContent(examPage);
        return PageResponseHandler.success(examResponses,examPage,"Get Success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PageResponse> update(@PathVariable Long id,@Valid @RequestBody ExamRequest request){
        return PageResponseHandler.success(examService.update(id,request),null,"Update Success");
    }

    @PatchMapping("/{id}/delete/{status}")
    public ResponseEntity<PageResponse> updateStatus(@PathVariable Long id,@PathVariable String status){
        return PageResponseHandler.success(examService.updateStatus(id,status),null,"Delete Success");
    }

    @DeleteMapping("/permanent-delete/{id}")
    public ResponseEntity<PageResponse> permanentDelete(@PathVariable Long id){
        return PageResponseHandler.success(examService.delete(id),null,"Record Deleted Success");
    }

}

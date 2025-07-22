package com.menglang.exam.controller;

import com.menglang.common.library.page.PageResponse;
import com.menglang.common.library.page.PageResponseHandler;
import com.menglang.exam.dto.scoreExam.ScoreExamRequest;
import com.menglang.exam.dto.scoreExam.ScoreExamResponse;
import com.menglang.exam.model.entities.ScoreExam;
import com.menglang.exam.service.scoreExam.ScoreExamService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/v1/score-exams")
@RequiredArgsConstructor
public class ScoreExamController {
    private static final Logger log = LoggerFactory.getLogger(ScoreExamController.class);
    private final ScoreExamService scoreExamService;

    @PostMapping
    public ResponseEntity<PageResponse> create(@Valid @RequestBody ScoreExamRequest request){
        return PageResponseHandler.success(scoreExamService.create(request),null,"Create Success");
    }

    @GetMapping("/{id}")
    public ResponseEntity<PageResponse> getById(@PathVariable Long id){
        return PageResponseHandler.success(scoreExamService.getById(id),null,"Get Success");
    }

    @GetMapping("get-all")
    public ResponseEntity<PageResponse> getAll(Map<String,String> params){
        Page<ScoreExam> scoreExamPage=scoreExamService.getAll(params);
        List<ScoreExamResponse> scoreExamResponses=scoreExamService.getPageContent(scoreExamPage);
        return PageResponseHandler.success(scoreExamResponses,scoreExamPage,"Get Success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<PageResponse> update(@PathVariable Long id,@Valid @RequestBody ScoreExamRequest request){
        return PageResponseHandler.success(scoreExamService.update(id,request),null,"Update Success");
    }


    @DeleteMapping("/permanent-delete/{id}")
    public ResponseEntity<PageResponse> permanentDelete(@PathVariable Long id){
        return PageResponseHandler.success(scoreExamService.delete(id),null,"Record Deleted Success");
    }

    @PostMapping("/batch")
    public ResponseEntity<PageResponse> batchSave(@RequestBody List<ScoreExamRequest> requests) {
        if (requests.isEmpty()) {
            return PageResponseHandler.error("No data","No Data", HttpStatusCode.valueOf(400));
        }

        log.info("Received {} score records", requests.size());

        // Handle logic in service layer
        return PageResponseHandler.success(
                scoreExamService.saveAllScoreExams(requests),
                null,
                "All records saved successfully"
        );
    }

}

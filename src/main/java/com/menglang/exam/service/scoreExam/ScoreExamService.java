package com.menglang.exam.service.scoreExam;

import com.menglang.exam.dto.scoreExam.ScoreExamRequest;
import com.menglang.exam.dto.scoreExam.ScoreExamResponse;
import com.menglang.exam.model.entities.ScoreExam;
import com.menglang.exam.service.BaseService;

import java.util.List;

public interface ScoreExamService extends BaseService<ScoreExamRequest, ScoreExamResponse, ScoreExam> {

    List<ScoreExamResponse> saveAllScoreExams(List<ScoreExamRequest> scoreExamRequestList);
}

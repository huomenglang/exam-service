package com.menglang.exam.service.exam;

import com.menglang.exam.dto.exam.ExamRequest;
import com.menglang.exam.dto.exam.ExamResponse;
import com.menglang.exam.model.entities.Exam;
import com.menglang.exam.service.BaseService;

public interface ExamService extends BaseService<ExamRequest, ExamResponse, Exam> {
    public ExamResponse updateStatus(Long id,String enabled);
}

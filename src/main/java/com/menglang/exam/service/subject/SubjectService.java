package com.menglang.exam.service.subject;
import com.menglang.exam.dto.subject.SubjectResponse;
import com.menglang.exam.feign.subject.SubjectClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class SubjectService {
    private final SubjectClient subjectClient;


    @Async
    public CompletableFuture<List<SubjectResponse>> getStudentByIds(List<Long> ids){
        return CompletableFuture.completedFuture(subjectClient.getSubjectsByIds(ids));
    }
}

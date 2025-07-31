package com.menglang.exam.service.student;

import com.menglang.exam.dto.student.StudentResponse;
import com.menglang.exam.feign.student.StudentClient;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentClient studentClient;

    @Async
    public CompletableFuture<List<StudentResponse>> getStudentsByIds(List<Long> ids){
        return CompletableFuture.completedFuture(studentClient.getStudentByIds(ids));
    }
}

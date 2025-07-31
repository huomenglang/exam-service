package com.menglang.exam.feign.subject;
import com.menglang.exam.dto.subject.SubjectResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "subject")
public interface SubjectClient {

    @PostMapping("/api/subjects/subject-ids")
    List<SubjectResponse> getSubjectsByIds(@RequestBody List<Long> ids);
}

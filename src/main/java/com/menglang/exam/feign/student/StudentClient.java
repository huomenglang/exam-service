package com.menglang.exam.feign.student;


import com.menglang.exam.dto.student.StudentResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "student")
public interface StudentClient {

    @PostMapping("/api/students/student-ids")
    List<StudentResponse> getStudentByIds(@RequestBody List<Long> ids);
}

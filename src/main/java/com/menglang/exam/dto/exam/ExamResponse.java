package com.menglang.exam.dto.exam;

import com.menglang.common.library.page.paginate.BasePageResponse;
import com.menglang.exam.model.enums.ExamType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ExamResponse extends BasePageResponse implements Serializable {
        private Long id;
        private String name;
        private ExamType examType;
        private String description;
        private Boolean enabled;
        private LocalDateTime createdAt;
        private String createdBy;
        private LocalDateTime updatedAt;
        private String updatedBy;
}

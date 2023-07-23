package com.edstem.course.contract;

import com.edstem.course.validation.ValidCourseId;
import com.edstem.course.validation.ValidStudentId;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registrations {
    private Long id;
    @ValidCourseId private Long courseId;
    @ValidStudentId private Long studentId;
}

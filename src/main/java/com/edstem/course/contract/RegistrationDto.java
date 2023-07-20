package com.edstem.course.contract;

import jakarta.validation.constraints.Min;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationDto {
        private Long id;
        @Min(value = 0, message = "Course Id should be a positive number")
        private Long courseId;
        @Min(value = 0, message = "Student Id should be a positive number")
        private Long studentId;
}

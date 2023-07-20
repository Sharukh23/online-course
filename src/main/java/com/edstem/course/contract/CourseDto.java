package com.edstem.course.contract;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDto {
    private Long id;
    @NotBlank(message = "Name is required")
    private String name;

    @Min(value = 0, message = "Capacity should be a positive number")
    private int capacity;

    @Min(value = 0, message = "Current enrollment should be a positive number")
    private int currentEnrollment;
}

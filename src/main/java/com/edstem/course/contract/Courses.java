package com.edstem.course.contract;

import com.edstem.course.validation.ValidCapacity;
import com.edstem.course.validation.ValidCurrentEnrollment;
import com.edstem.course.validation.ValidName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Courses {
    private Long id;
    @ValidName private String name;
    @ValidCapacity private int capacity;
    @ValidCurrentEnrollment private int currentEnrollment;
}

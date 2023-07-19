package com.edstem.Course.contract;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Courses {
    private Long id;
    private String name;
    private int capacity;
    private int currentEnrollment;
}

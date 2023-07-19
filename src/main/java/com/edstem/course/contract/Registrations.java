package com.edstem.course.contract;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registrations {
        private Long id;
        private Long courseId;
        private Long studentId;
}

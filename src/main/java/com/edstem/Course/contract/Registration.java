package com.edstem.Course.contract;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Registration {
        private Long id;
        private Long courseId;
        private Long studentId;
}

package com.edstem.course.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;

@Entity
@Table(name = "Registrations")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 0, message = "Course Id should be a positive number")
    private Long courseId;
    @Min(value = 0, message = "Student Id should be a positive number")
    private Long studentId;
}
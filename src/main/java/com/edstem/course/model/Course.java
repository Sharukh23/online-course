package com.edstem.course.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "courses")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int capacity;
    private int currentEnrollment;
}
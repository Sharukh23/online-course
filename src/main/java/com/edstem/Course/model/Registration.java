package com.edstem.Course.model;

import jakarta.persistence.*;
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
    private Long courseId;
    private Long studentId;
}

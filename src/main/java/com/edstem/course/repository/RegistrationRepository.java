package com.edstem.course.repository;

import com.edstem.course.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration, Long> {
    boolean existsByStudentIdAndCourseId(
            @Param("studentId") Long studentId, @Param("courseId") Long courseId);
}

package com.edstem.course.repository;

import com.edstem.course.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<Registration,Long> {
    boolean existsByStudentId(Long studentId);
}

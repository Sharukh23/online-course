package com.edstem.Course.repository;

import com.edstem.Course.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    boolean existsCourseByName(@Param("name") String name);
}

package com.edstem.Course.controller;

import com.edstem.Course.contract.Courses;
import com.edstem.Course.exception.CourseNameNullException;
import com.edstem.Course.model.Course;
import com.edstem.Course.service.CourseService;
import com.edstem.Course.exception.CourseAlreadyExistsException;
import com.edstem.Course.exception.EnrollmentCapacityException;
import com.edstem.Course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping
@RestController
public class CourseController {
    private final CourseService courseService;
    private final CourseRepository courseRepository;

    @Autowired
    public CourseController(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }


    @GetMapping("/courses")
    public ResponseEntity<List<Courses>> getAllCourses() {
        List<Courses> courses = courseService.getAllCourses();
        if (courses.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        return ResponseEntity.ok(course);
    }

    @PostMapping("/courses")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        if (course.getName().isEmpty()) {
            throw new CourseNameNullException();
        }
        if (course.getCurrentEnrollment() > course.getCapacity()) {
            throw new EnrollmentCapacityException(course.getCurrentEnrollment(), course.getCapacity());
        }
        boolean courseExists = courseRepository.existsCourseByName(course.getName());
        if (courseExists) {
            throw new CourseAlreadyExistsException(course.getName());
        }
        Course addedCourse = courseRepository.save(course);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedCourse);
    }

    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourseById(@PathVariable Long id, @RequestBody Courses newCourseData) {
        Optional<Course> oldCourseData = courseRepository.findById(id);
        if (newCourseData.getCurrentEnrollment() > newCourseData.getCapacity()) {
            throw new EnrollmentCapacityException(newCourseData.getCurrentEnrollment(), newCourseData.getCapacity());
        }
        if (oldCourseData.isPresent()) {
            Course updatedCourse = oldCourseData.get();
            updatedCourse.setName(newCourseData.getName());
            updatedCourse.setCapacity(newCourseData.getCapacity());
            updatedCourse.setCurrentEnrollment(newCourseData.getCurrentEnrollment());
            Course savedCourse = courseRepository.save(updatedCourse);
            return new ResponseEntity<>(savedCourse, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/courses/{id}")
    public ResponseEntity<HttpStatus> deleteCourseById(@PathVariable Long id) {
        courseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
